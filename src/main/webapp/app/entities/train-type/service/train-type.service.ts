import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrainType, NewTrainType } from '../train-type.model';

export type PartialUpdateTrainType = Partial<ITrainType> & Pick<ITrainType, 'id'>;

export type EntityResponseType = HttpResponse<ITrainType>;
export type EntityArrayResponseType = HttpResponse<ITrainType[]>;

@Injectable({ providedIn: 'root' })
export class TrainTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/train-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(trainType: NewTrainType): Observable<EntityResponseType> {
    return this.http.post<ITrainType>(this.resourceUrl, trainType, { observe: 'response' });
  }

  update(trainType: ITrainType): Observable<EntityResponseType> {
    return this.http.put<ITrainType>(`${this.resourceUrl}/${this.getTrainTypeIdentifier(trainType)}`, trainType, { observe: 'response' });
  }

  partialUpdate(trainType: PartialUpdateTrainType): Observable<EntityResponseType> {
    return this.http.patch<ITrainType>(`${this.resourceUrl}/${this.getTrainTypeIdentifier(trainType)}`, trainType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainTypeIdentifier(trainType: Pick<ITrainType, 'id'>): number {
    return trainType.id;
  }

  compareTrainType(o1: Pick<ITrainType, 'id'> | null, o2: Pick<ITrainType, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainTypeIdentifier(o1) === this.getTrainTypeIdentifier(o2) : o1 === o2;
  }

  addTrainTypeToCollectionIfMissing<Type extends Pick<ITrainType, 'id'>>(
    trainTypeCollection: Type[],
    ...trainTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trainTypes: Type[] = trainTypesToCheck.filter(isPresent);
    if (trainTypes.length > 0) {
      const trainTypeCollectionIdentifiers = trainTypeCollection.map(trainTypeItem => this.getTrainTypeIdentifier(trainTypeItem)!);
      const trainTypesToAdd = trainTypes.filter(trainTypeItem => {
        const trainTypeIdentifier = this.getTrainTypeIdentifier(trainTypeItem);
        if (trainTypeCollectionIdentifiers.includes(trainTypeIdentifier)) {
          return false;
        }
        trainTypeCollectionIdentifiers.push(trainTypeIdentifier);
        return true;
      });
      return [...trainTypesToAdd, ...trainTypeCollection];
    }
    return trainTypeCollection;
  }
}

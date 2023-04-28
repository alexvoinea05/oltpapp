import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITrain, NewTrain } from '../train.model';

export type PartialUpdateTrain = Partial<ITrain> & Pick<ITrain, 'id'>;

export type EntityResponseType = HttpResponse<ITrain>;
export type EntityArrayResponseType = HttpResponse<ITrain[]>;

@Injectable({ providedIn: 'root' })
export class TrainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/trains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(train: NewTrain): Observable<EntityResponseType> {
    return this.http.post<ITrain>(this.resourceUrl, train, { observe: 'response' });
  }

  update(train: ITrain): Observable<EntityResponseType> {
    return this.http.put<ITrain>(`${this.resourceUrl}/${this.getTrainIdentifier(train)}`, train, { observe: 'response' });
  }

  partialUpdate(train: PartialUpdateTrain): Observable<EntityResponseType> {
    return this.http.patch<ITrain>(`${this.resourceUrl}/${this.getTrainIdentifier(train)}`, train, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTrainIdentifier(train: Pick<ITrain, 'id'>): number {
    return train.id;
  }

  compareTrain(o1: Pick<ITrain, 'id'> | null, o2: Pick<ITrain, 'id'> | null): boolean {
    return o1 && o2 ? this.getTrainIdentifier(o1) === this.getTrainIdentifier(o2) : o1 === o2;
  }

  addTrainToCollectionIfMissing<Type extends Pick<ITrain, 'id'>>(
    trainCollection: Type[],
    ...trainsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const trains: Type[] = trainsToCheck.filter(isPresent);
    if (trains.length > 0) {
      const trainCollectionIdentifiers = trainCollection.map(trainItem => this.getTrainIdentifier(trainItem)!);
      const trainsToAdd = trains.filter(trainItem => {
        const trainIdentifier = this.getTrainIdentifier(trainItem);
        if (trainCollectionIdentifiers.includes(trainIdentifier)) {
          return false;
        }
        trainCollectionIdentifiers.push(trainIdentifier);
        return true;
      });
      return [...trainsToAdd, ...trainCollection];
    }
    return trainCollection;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayType, NewRailwayType } from '../railway-type.model';

export type PartialUpdateRailwayType = Partial<IRailwayType> & Pick<IRailwayType, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayType>;
export type EntityArrayResponseType = HttpResponse<IRailwayType[]>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayType: NewRailwayType): Observable<EntityResponseType> {
    return this.http.post<IRailwayType>(this.resourceUrl, railwayType, { observe: 'response' });
  }

  update(railwayType: IRailwayType): Observable<EntityResponseType> {
    return this.http.put<IRailwayType>(`${this.resourceUrl}/${this.getRailwayTypeIdentifier(railwayType)}`, railwayType, {
      observe: 'response',
    });
  }

  partialUpdate(railwayType: PartialUpdateRailwayType): Observable<EntityResponseType> {
    return this.http.patch<IRailwayType>(`${this.resourceUrl}/${this.getRailwayTypeIdentifier(railwayType)}`, railwayType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayTypeIdentifier(railwayType: Pick<IRailwayType, 'id'>): number {
    return railwayType.id;
  }

  compareRailwayType(o1: Pick<IRailwayType, 'id'> | null, o2: Pick<IRailwayType, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayTypeIdentifier(o1) === this.getRailwayTypeIdentifier(o2) : o1 === o2;
  }

  addRailwayTypeToCollectionIfMissing<Type extends Pick<IRailwayType, 'id'>>(
    railwayTypeCollection: Type[],
    ...railwayTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayTypes: Type[] = railwayTypesToCheck.filter(isPresent);
    if (railwayTypes.length > 0) {
      const railwayTypeCollectionIdentifiers = railwayTypeCollection.map(
        railwayTypeItem => this.getRailwayTypeIdentifier(railwayTypeItem)!
      );
      const railwayTypesToAdd = railwayTypes.filter(railwayTypeItem => {
        const railwayTypeIdentifier = this.getRailwayTypeIdentifier(railwayTypeItem);
        if (railwayTypeCollectionIdentifiers.includes(railwayTypeIdentifier)) {
          return false;
        }
        railwayTypeCollectionIdentifiers.push(railwayTypeIdentifier);
        return true;
      });
      return [...railwayTypesToAdd, ...railwayTypeCollection];
    }
    return railwayTypeCollection;
  }
}

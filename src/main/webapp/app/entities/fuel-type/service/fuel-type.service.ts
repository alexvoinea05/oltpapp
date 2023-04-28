import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuelType, NewFuelType } from '../fuel-type.model';

export type PartialUpdateFuelType = Partial<IFuelType> & Pick<IFuelType, 'id'>;

export type EntityResponseType = HttpResponse<IFuelType>;
export type EntityArrayResponseType = HttpResponse<IFuelType[]>;

@Injectable({ providedIn: 'root' })
export class FuelTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fuel-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(fuelType: NewFuelType): Observable<EntityResponseType> {
    return this.http.post<IFuelType>(this.resourceUrl, fuelType, { observe: 'response' });
  }

  update(fuelType: IFuelType): Observable<EntityResponseType> {
    return this.http.put<IFuelType>(`${this.resourceUrl}/${this.getFuelTypeIdentifier(fuelType)}`, fuelType, { observe: 'response' });
  }

  partialUpdate(fuelType: PartialUpdateFuelType): Observable<EntityResponseType> {
    return this.http.patch<IFuelType>(`${this.resourceUrl}/${this.getFuelTypeIdentifier(fuelType)}`, fuelType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuelType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuelType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuelTypeIdentifier(fuelType: Pick<IFuelType, 'id'>): number {
    return fuelType.id;
  }

  compareFuelType(o1: Pick<IFuelType, 'id'> | null, o2: Pick<IFuelType, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuelTypeIdentifier(o1) === this.getFuelTypeIdentifier(o2) : o1 === o2;
  }

  addFuelTypeToCollectionIfMissing<Type extends Pick<IFuelType, 'id'>>(
    fuelTypeCollection: Type[],
    ...fuelTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fuelTypes: Type[] = fuelTypesToCheck.filter(isPresent);
    if (fuelTypes.length > 0) {
      const fuelTypeCollectionIdentifiers = fuelTypeCollection.map(fuelTypeItem => this.getFuelTypeIdentifier(fuelTypeItem)!);
      const fuelTypesToAdd = fuelTypes.filter(fuelTypeItem => {
        const fuelTypeIdentifier = this.getFuelTypeIdentifier(fuelTypeItem);
        if (fuelTypeCollectionIdentifiers.includes(fuelTypeIdentifier)) {
          return false;
        }
        fuelTypeCollectionIdentifiers.push(fuelTypeIdentifier);
        return true;
      });
      return [...fuelTypesToAdd, ...fuelTypeCollection];
    }
    return fuelTypeCollection;
  }
}

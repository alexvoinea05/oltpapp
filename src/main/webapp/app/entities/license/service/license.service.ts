import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILicense, NewLicense } from '../license.model';

export type PartialUpdateLicense = Partial<ILicense> & Pick<ILicense, 'id'>;

export type EntityResponseType = HttpResponse<ILicense>;
export type EntityArrayResponseType = HttpResponse<ILicense[]>;

@Injectable({ providedIn: 'root' })
export class LicenseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/licenses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(license: NewLicense): Observable<EntityResponseType> {
    return this.http.post<ILicense>(this.resourceUrl, license, { observe: 'response' });
  }

  update(license: ILicense): Observable<EntityResponseType> {
    return this.http.put<ILicense>(`${this.resourceUrl}/${this.getLicenseIdentifier(license)}`, license, { observe: 'response' });
  }

  partialUpdate(license: PartialUpdateLicense): Observable<EntityResponseType> {
    return this.http.patch<ILicense>(`${this.resourceUrl}/${this.getLicenseIdentifier(license)}`, license, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILicense>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILicense[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getLicenseIdentifier(license: Pick<ILicense, 'id'>): number {
    return license.id;
  }

  compareLicense(o1: Pick<ILicense, 'id'> | null, o2: Pick<ILicense, 'id'> | null): boolean {
    return o1 && o2 ? this.getLicenseIdentifier(o1) === this.getLicenseIdentifier(o2) : o1 === o2;
  }

  addLicenseToCollectionIfMissing<Type extends Pick<ILicense, 'id'>>(
    licenseCollection: Type[],
    ...licensesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const licenses: Type[] = licensesToCheck.filter(isPresent);
    if (licenses.length > 0) {
      const licenseCollectionIdentifiers = licenseCollection.map(licenseItem => this.getLicenseIdentifier(licenseItem)!);
      const licensesToAdd = licenses.filter(licenseItem => {
        const licenseIdentifier = this.getLicenseIdentifier(licenseItem);
        if (licenseCollectionIdentifiers.includes(licenseIdentifier)) {
          return false;
        }
        licenseCollectionIdentifiers.push(licenseIdentifier);
        return true;
      });
      return [...licensesToAdd, ...licenseCollection];
    }
    return licenseCollection;
  }
}

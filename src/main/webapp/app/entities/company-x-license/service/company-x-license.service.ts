import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICompanyXLicense, NewCompanyXLicense } from '../company-x-license.model';

export type PartialUpdateCompanyXLicense = Partial<ICompanyXLicense> & Pick<ICompanyXLicense, 'id'>;

export type EntityResponseType = HttpResponse<ICompanyXLicense>;
export type EntityArrayResponseType = HttpResponse<ICompanyXLicense[]>;

@Injectable({ providedIn: 'root' })
export class CompanyXLicenseService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/company-x-licenses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(companyXLicense: NewCompanyXLicense): Observable<EntityResponseType> {
    return this.http.post<ICompanyXLicense>(this.resourceUrl, companyXLicense, { observe: 'response' });
  }

  update(companyXLicense: ICompanyXLicense): Observable<EntityResponseType> {
    return this.http.put<ICompanyXLicense>(`${this.resourceUrl}/${this.getCompanyXLicenseIdentifier(companyXLicense)}`, companyXLicense, {
      observe: 'response',
    });
  }

  partialUpdate(companyXLicense: PartialUpdateCompanyXLicense): Observable<EntityResponseType> {
    return this.http.patch<ICompanyXLicense>(`${this.resourceUrl}/${this.getCompanyXLicenseIdentifier(companyXLicense)}`, companyXLicense, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompanyXLicense>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompanyXLicense[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCompanyXLicenseIdentifier(companyXLicense: Pick<ICompanyXLicense, 'id'>): number {
    return companyXLicense.id;
  }

  compareCompanyXLicense(o1: Pick<ICompanyXLicense, 'id'> | null, o2: Pick<ICompanyXLicense, 'id'> | null): boolean {
    return o1 && o2 ? this.getCompanyXLicenseIdentifier(o1) === this.getCompanyXLicenseIdentifier(o2) : o1 === o2;
  }

  addCompanyXLicenseToCollectionIfMissing<Type extends Pick<ICompanyXLicense, 'id'>>(
    companyXLicenseCollection: Type[],
    ...companyXLicensesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const companyXLicenses: Type[] = companyXLicensesToCheck.filter(isPresent);
    if (companyXLicenses.length > 0) {
      const companyXLicenseCollectionIdentifiers = companyXLicenseCollection.map(
        companyXLicenseItem => this.getCompanyXLicenseIdentifier(companyXLicenseItem)!
      );
      const companyXLicensesToAdd = companyXLicenses.filter(companyXLicenseItem => {
        const companyXLicenseIdentifier = this.getCompanyXLicenseIdentifier(companyXLicenseItem);
        if (companyXLicenseCollectionIdentifiers.includes(companyXLicenseIdentifier)) {
          return false;
        }
        companyXLicenseCollectionIdentifiers.push(companyXLicenseIdentifier);
        return true;
      });
      return [...companyXLicensesToAdd, ...companyXLicenseCollection];
    }
    return companyXLicenseCollection;
  }
}

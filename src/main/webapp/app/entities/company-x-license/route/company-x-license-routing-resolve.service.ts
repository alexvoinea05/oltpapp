import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompanyXLicense } from '../company-x-license.model';
import { CompanyXLicenseService } from '../service/company-x-license.service';

@Injectable({ providedIn: 'root' })
export class CompanyXLicenseRoutingResolveService implements Resolve<ICompanyXLicense | null> {
  constructor(protected service: CompanyXLicenseService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICompanyXLicense | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((companyXLicense: HttpResponse<ICompanyXLicense>) => {
          if (companyXLicense.body) {
            return of(companyXLicense.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}

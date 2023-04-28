import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuelType } from '../fuel-type.model';
import { FuelTypeService } from '../service/fuel-type.service';

@Injectable({ providedIn: 'root' })
export class FuelTypeRoutingResolveService implements Resolve<IFuelType | null> {
  constructor(protected service: FuelTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFuelType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((fuelType: HttpResponse<IFuelType>) => {
          if (fuelType.body) {
            return of(fuelType.body);
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

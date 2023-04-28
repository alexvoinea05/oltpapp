import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayType } from '../railway-type.model';
import { RailwayTypeService } from '../service/railway-type.service';

@Injectable({ providedIn: 'root' })
export class RailwayTypeRoutingResolveService implements Resolve<IRailwayType | null> {
  constructor(protected service: RailwayTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayType: HttpResponse<IRailwayType>) => {
          if (railwayType.body) {
            return of(railwayType.body);
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

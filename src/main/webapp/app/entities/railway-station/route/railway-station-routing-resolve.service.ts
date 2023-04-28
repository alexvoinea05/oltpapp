import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRailwayStation } from '../railway-station.model';
import { RailwayStationService } from '../service/railway-station.service';

@Injectable({ providedIn: 'root' })
export class RailwayStationRoutingResolveService implements Resolve<IRailwayStation | null> {
  constructor(protected service: RailwayStationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRailwayStation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((railwayStation: HttpResponse<IRailwayStation>) => {
          if (railwayStation.body) {
            return of(railwayStation.body);
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

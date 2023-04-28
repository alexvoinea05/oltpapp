import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourneyStatus } from '../journey-status.model';
import { JourneyStatusService } from '../service/journey-status.service';

@Injectable({ providedIn: 'root' })
export class JourneyStatusRoutingResolveService implements Resolve<IJourneyStatus | null> {
  constructor(protected service: JourneyStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourneyStatus | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journeyStatus: HttpResponse<IJourneyStatus>) => {
          if (journeyStatus.body) {
            return of(journeyStatus.body);
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

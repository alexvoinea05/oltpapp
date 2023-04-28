import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIncidentStatus } from '../incident-status.model';
import { IncidentStatusService } from '../service/incident-status.service';

@Injectable({ providedIn: 'root' })
export class IncidentStatusRoutingResolveService implements Resolve<IIncidentStatus | null> {
  constructor(protected service: IncidentStatusService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncidentStatus | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((incidentStatus: HttpResponse<IIncidentStatus>) => {
          if (incidentStatus.body) {
            return of(incidentStatus.body);
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

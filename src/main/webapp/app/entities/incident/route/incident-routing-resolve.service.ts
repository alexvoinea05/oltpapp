import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IIncident } from '../incident.model';
import { IncidentService } from '../service/incident.service';

@Injectable({ providedIn: 'root' })
export class IncidentRoutingResolveService implements Resolve<IIncident | null> {
  constructor(protected service: IncidentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncident | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((incident: HttpResponse<IIncident>) => {
          if (incident.body) {
            return of(incident.body);
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

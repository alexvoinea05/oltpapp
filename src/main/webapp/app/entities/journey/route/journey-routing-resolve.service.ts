import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJourney } from '../journey.model';
import { JourneyService } from '../service/journey.service';

@Injectable({ providedIn: 'root' })
export class JourneyRoutingResolveService implements Resolve<IJourney | null> {
  constructor(protected service: JourneyService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IJourney | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((journey: HttpResponse<IJourney>) => {
          if (journey.body) {
            return of(journey.body);
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

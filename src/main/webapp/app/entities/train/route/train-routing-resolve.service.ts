import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrain } from '../train.model';
import { TrainService } from '../service/train.service';

@Injectable({ providedIn: 'root' })
export class TrainRoutingResolveService implements Resolve<ITrain | null> {
  constructor(protected service: TrainService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrain | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((train: HttpResponse<ITrain>) => {
          if (train.body) {
            return of(train.body);
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

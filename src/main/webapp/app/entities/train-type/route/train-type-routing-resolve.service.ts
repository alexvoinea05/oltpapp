import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITrainType } from '../train-type.model';
import { TrainTypeService } from '../service/train-type.service';

@Injectable({ providedIn: 'root' })
export class TrainTypeRoutingResolveService implements Resolve<ITrainType | null> {
  constructor(protected service: TrainTypeService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainType | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((trainType: HttpResponse<ITrainType>) => {
          if (trainType.body) {
            return of(trainType.body);
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

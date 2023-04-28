import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainTypeComponent } from '../list/train-type.component';
import { TrainTypeDetailComponent } from '../detail/train-type-detail.component';
import { TrainTypeUpdateComponent } from '../update/train-type-update.component';
import { TrainTypeRoutingResolveService } from './train-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainTypeRoute: Routes = [
  {
    path: '',
    component: TrainTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainTypeDetailComponent,
    resolve: {
      trainType: TrainTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainTypeUpdateComponent,
    resolve: {
      trainType: TrainTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainTypeUpdateComponent,
    resolve: {
      trainType: TrainTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainTypeRoute)],
  exports: [RouterModule],
})
export class TrainTypeRoutingModule {}

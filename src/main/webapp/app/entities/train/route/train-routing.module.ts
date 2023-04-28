import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TrainComponent } from '../list/train.component';
import { TrainDetailComponent } from '../detail/train-detail.component';
import { TrainUpdateComponent } from '../update/train-update.component';
import { TrainRoutingResolveService } from './train-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const trainRoute: Routes = [
  {
    path: '',
    component: TrainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TrainDetailComponent,
    resolve: {
      train: TrainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TrainUpdateComponent,
    resolve: {
      train: TrainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TrainUpdateComponent,
    resolve: {
      train: TrainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(trainRoute)],
  exports: [RouterModule],
})
export class TrainRoutingModule {}

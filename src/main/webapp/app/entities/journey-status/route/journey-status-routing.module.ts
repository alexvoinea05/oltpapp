import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { JourneyStatusComponent } from '../list/journey-status.component';
import { JourneyStatusDetailComponent } from '../detail/journey-status-detail.component';
import { JourneyStatusUpdateComponent } from '../update/journey-status-update.component';
import { JourneyStatusRoutingResolveService } from './journey-status-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const journeyStatusRoute: Routes = [
  {
    path: '',
    component: JourneyStatusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JourneyStatusDetailComponent,
    resolve: {
      journeyStatus: JourneyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JourneyStatusUpdateComponent,
    resolve: {
      journeyStatus: JourneyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JourneyStatusUpdateComponent,
    resolve: {
      journeyStatus: JourneyStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(journeyStatusRoute)],
  exports: [RouterModule],
})
export class JourneyStatusRoutingModule {}

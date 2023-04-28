import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { IncidentStatusComponent } from '../list/incident-status.component';
import { IncidentStatusDetailComponent } from '../detail/incident-status-detail.component';
import { IncidentStatusUpdateComponent } from '../update/incident-status-update.component';
import { IncidentStatusRoutingResolveService } from './incident-status-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const incidentStatusRoute: Routes = [
  {
    path: '',
    component: IncidentStatusComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncidentStatusDetailComponent,
    resolve: {
      incidentStatus: IncidentStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncidentStatusUpdateComponent,
    resolve: {
      incidentStatus: IncidentStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncidentStatusUpdateComponent,
    resolve: {
      incidentStatus: IncidentStatusRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(incidentStatusRoute)],
  exports: [RouterModule],
})
export class IncidentStatusRoutingModule {}

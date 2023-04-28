import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayStationComponent } from '../list/railway-station.component';
import { RailwayStationDetailComponent } from '../detail/railway-station-detail.component';
import { RailwayStationUpdateComponent } from '../update/railway-station-update.component';
import { RailwayStationRoutingResolveService } from './railway-station-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayStationRoute: Routes = [
  {
    path: '',
    component: RailwayStationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayStationDetailComponent,
    resolve: {
      railwayStation: RailwayStationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayStationUpdateComponent,
    resolve: {
      railwayStation: RailwayStationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayStationUpdateComponent,
    resolve: {
      railwayStation: RailwayStationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayStationRoute)],
  exports: [RouterModule],
})
export class RailwayStationRoutingModule {}

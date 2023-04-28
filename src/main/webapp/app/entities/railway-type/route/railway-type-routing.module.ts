import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RailwayTypeComponent } from '../list/railway-type.component';
import { RailwayTypeDetailComponent } from '../detail/railway-type-detail.component';
import { RailwayTypeUpdateComponent } from '../update/railway-type-update.component';
import { RailwayTypeRoutingResolveService } from './railway-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const railwayTypeRoute: Routes = [
  {
    path: '',
    component: RailwayTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RailwayTypeDetailComponent,
    resolve: {
      railwayType: RailwayTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RailwayTypeUpdateComponent,
    resolve: {
      railwayType: RailwayTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RailwayTypeUpdateComponent,
    resolve: {
      railwayType: RailwayTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(railwayTypeRoute)],
  exports: [RouterModule],
})
export class RailwayTypeRoutingModule {}

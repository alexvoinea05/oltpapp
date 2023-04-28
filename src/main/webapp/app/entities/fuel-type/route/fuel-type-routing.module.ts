import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FuelTypeComponent } from '../list/fuel-type.component';
import { FuelTypeDetailComponent } from '../detail/fuel-type-detail.component';
import { FuelTypeUpdateComponent } from '../update/fuel-type-update.component';
import { FuelTypeRoutingResolveService } from './fuel-type-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const fuelTypeRoute: Routes = [
  {
    path: '',
    component: FuelTypeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FuelTypeDetailComponent,
    resolve: {
      fuelType: FuelTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FuelTypeUpdateComponent,
    resolve: {
      fuelType: FuelTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FuelTypeUpdateComponent,
    resolve: {
      fuelType: FuelTypeRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(fuelTypeRoute)],
  exports: [RouterModule],
})
export class FuelTypeRoutingModule {}

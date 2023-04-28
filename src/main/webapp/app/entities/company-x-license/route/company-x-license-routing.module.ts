import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CompanyXLicenseComponent } from '../list/company-x-license.component';
import { CompanyXLicenseDetailComponent } from '../detail/company-x-license-detail.component';
import { CompanyXLicenseUpdateComponent } from '../update/company-x-license-update.component';
import { CompanyXLicenseRoutingResolveService } from './company-x-license-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const companyXLicenseRoute: Routes = [
  {
    path: '',
    component: CompanyXLicenseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CompanyXLicenseDetailComponent,
    resolve: {
      companyXLicense: CompanyXLicenseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CompanyXLicenseUpdateComponent,
    resolve: {
      companyXLicense: CompanyXLicenseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CompanyXLicenseUpdateComponent,
    resolve: {
      companyXLicense: CompanyXLicenseRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(companyXLicenseRoute)],
  exports: [RouterModule],
})
export class CompanyXLicenseRoutingModule {}

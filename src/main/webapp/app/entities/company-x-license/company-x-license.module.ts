import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CompanyXLicenseComponent } from './list/company-x-license.component';
import { CompanyXLicenseDetailComponent } from './detail/company-x-license-detail.component';
import { CompanyXLicenseUpdateComponent } from './update/company-x-license-update.component';
import { CompanyXLicenseDeleteDialogComponent } from './delete/company-x-license-delete-dialog.component';
import { CompanyXLicenseRoutingModule } from './route/company-x-license-routing.module';

@NgModule({
  imports: [SharedModule, CompanyXLicenseRoutingModule],
  declarations: [
    CompanyXLicenseComponent,
    CompanyXLicenseDetailComponent,
    CompanyXLicenseUpdateComponent,
    CompanyXLicenseDeleteDialogComponent,
  ],
})
export class CompanyXLicenseModule {}

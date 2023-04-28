import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { FuelTypeComponent } from './list/fuel-type.component';
import { FuelTypeDetailComponent } from './detail/fuel-type-detail.component';
import { FuelTypeUpdateComponent } from './update/fuel-type-update.component';
import { FuelTypeDeleteDialogComponent } from './delete/fuel-type-delete-dialog.component';
import { FuelTypeRoutingModule } from './route/fuel-type-routing.module';

@NgModule({
  imports: [SharedModule, FuelTypeRoutingModule],
  declarations: [FuelTypeComponent, FuelTypeDetailComponent, FuelTypeUpdateComponent, FuelTypeDeleteDialogComponent],
})
export class FuelTypeModule {}

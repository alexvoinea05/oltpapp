import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayTypeComponent } from './list/railway-type.component';
import { RailwayTypeDetailComponent } from './detail/railway-type-detail.component';
import { RailwayTypeUpdateComponent } from './update/railway-type-update.component';
import { RailwayTypeDeleteDialogComponent } from './delete/railway-type-delete-dialog.component';
import { RailwayTypeRoutingModule } from './route/railway-type-routing.module';

@NgModule({
  imports: [SharedModule, RailwayTypeRoutingModule],
  declarations: [RailwayTypeComponent, RailwayTypeDetailComponent, RailwayTypeUpdateComponent, RailwayTypeDeleteDialogComponent],
})
export class RailwayTypeModule {}

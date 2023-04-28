import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RailwayStationComponent } from './list/railway-station.component';
import { RailwayStationDetailComponent } from './detail/railway-station-detail.component';
import { RailwayStationUpdateComponent } from './update/railway-station-update.component';
import { RailwayStationDeleteDialogComponent } from './delete/railway-station-delete-dialog.component';
import { RailwayStationRoutingModule } from './route/railway-station-routing.module';

@NgModule({
  imports: [SharedModule, RailwayStationRoutingModule],
  declarations: [
    RailwayStationComponent,
    RailwayStationDetailComponent,
    RailwayStationUpdateComponent,
    RailwayStationDeleteDialogComponent,
  ],
})
export class RailwayStationModule {}

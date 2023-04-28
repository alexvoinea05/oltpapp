import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { IncidentStatusComponent } from './list/incident-status.component';
import { IncidentStatusDetailComponent } from './detail/incident-status-detail.component';
import { IncidentStatusUpdateComponent } from './update/incident-status-update.component';
import { IncidentStatusDeleteDialogComponent } from './delete/incident-status-delete-dialog.component';
import { IncidentStatusRoutingModule } from './route/incident-status-routing.module';

@NgModule({
  imports: [SharedModule, IncidentStatusRoutingModule],
  declarations: [
    IncidentStatusComponent,
    IncidentStatusDetailComponent,
    IncidentStatusUpdateComponent,
    IncidentStatusDeleteDialogComponent,
  ],
})
export class IncidentStatusModule {}

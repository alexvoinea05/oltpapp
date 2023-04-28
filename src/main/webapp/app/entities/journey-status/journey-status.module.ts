import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { JourneyStatusComponent } from './list/journey-status.component';
import { JourneyStatusDetailComponent } from './detail/journey-status-detail.component';
import { JourneyStatusUpdateComponent } from './update/journey-status-update.component';
import { JourneyStatusDeleteDialogComponent } from './delete/journey-status-delete-dialog.component';
import { JourneyStatusRoutingModule } from './route/journey-status-routing.module';

@NgModule({
  imports: [SharedModule, JourneyStatusRoutingModule],
  declarations: [JourneyStatusComponent, JourneyStatusDetailComponent, JourneyStatusUpdateComponent, JourneyStatusDeleteDialogComponent],
})
export class JourneyStatusModule {}

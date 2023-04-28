import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainComponent } from './list/train.component';
import { TrainDetailComponent } from './detail/train-detail.component';
import { TrainUpdateComponent } from './update/train-update.component';
import { TrainDeleteDialogComponent } from './delete/train-delete-dialog.component';
import { TrainRoutingModule } from './route/train-routing.module';

@NgModule({
  imports: [SharedModule, TrainRoutingModule],
  declarations: [TrainComponent, TrainDetailComponent, TrainUpdateComponent, TrainDeleteDialogComponent],
})
export class TrainModule {}

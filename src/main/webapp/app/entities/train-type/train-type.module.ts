import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TrainTypeComponent } from './list/train-type.component';
import { TrainTypeDetailComponent } from './detail/train-type-detail.component';
import { TrainTypeUpdateComponent } from './update/train-type-update.component';
import { TrainTypeDeleteDialogComponent } from './delete/train-type-delete-dialog.component';
import { TrainTypeRoutingModule } from './route/train-type-routing.module';

@NgModule({
  imports: [SharedModule, TrainTypeRoutingModule],
  declarations: [TrainTypeComponent, TrainTypeDetailComponent, TrainTypeUpdateComponent, TrainTypeDeleteDialogComponent],
})
export class TrainTypeModule {}

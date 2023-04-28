import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRailwayStation } from '../railway-station.model';
import { RailwayStationService } from '../service/railway-station.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './railway-station-delete-dialog.component.html',
})
export class RailwayStationDeleteDialogComponent {
  railwayStation?: IRailwayStation;

  constructor(protected railwayStationService: RailwayStationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.railwayStationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

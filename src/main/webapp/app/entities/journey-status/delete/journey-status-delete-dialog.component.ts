import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IJourneyStatus } from '../journey-status.model';
import { JourneyStatusService } from '../service/journey-status.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './journey-status-delete-dialog.component.html',
})
export class JourneyStatusDeleteDialogComponent {
  journeyStatus?: IJourneyStatus;

  constructor(protected journeyStatusService: JourneyStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.journeyStatusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

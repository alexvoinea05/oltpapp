import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncidentStatus } from '../incident-status.model';
import { IncidentStatusService } from '../service/incident-status.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './incident-status-delete-dialog.component.html',
})
export class IncidentStatusDeleteDialogComponent {
  incidentStatus?: IIncidentStatus;

  constructor(protected incidentStatusService: IncidentStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incidentStatusService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

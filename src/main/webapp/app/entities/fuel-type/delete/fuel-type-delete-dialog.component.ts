import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFuelType } from '../fuel-type.model';
import { FuelTypeService } from '../service/fuel-type.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './fuel-type-delete-dialog.component.html',
})
export class FuelTypeDeleteDialogComponent {
  fuelType?: IFuelType;

  constructor(protected fuelTypeService: FuelTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fuelTypeService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

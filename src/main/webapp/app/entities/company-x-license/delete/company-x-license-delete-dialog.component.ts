import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompanyXLicense } from '../company-x-license.model';
import { CompanyXLicenseService } from '../service/company-x-license.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './company-x-license-delete-dialog.component.html',
})
export class CompanyXLicenseDeleteDialogComponent {
  companyXLicense?: ICompanyXLicense;

  constructor(protected companyXLicenseService: CompanyXLicenseService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.companyXLicenseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

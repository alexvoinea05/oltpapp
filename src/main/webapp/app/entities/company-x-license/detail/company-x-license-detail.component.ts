import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompanyXLicense } from '../company-x-license.model';

@Component({
  selector: 'jhi-company-x-license-detail',
  templateUrl: './company-x-license-detail.component.html',
})
export class CompanyXLicenseDetailComponent implements OnInit {
  companyXLicense: ICompanyXLicense | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyXLicense }) => {
      this.companyXLicense = companyXLicense;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

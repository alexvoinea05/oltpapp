import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { CompanyXLicenseFormService, CompanyXLicenseFormGroup } from './company-x-license-form.service';
import { ICompanyXLicense } from '../company-x-license.model';
import { CompanyXLicenseService } from '../service/company-x-license.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ILicense } from 'app/entities/license/license.model';
import { LicenseService } from 'app/entities/license/service/license.service';

@Component({
  selector: 'jhi-company-x-license-update',
  templateUrl: './company-x-license-update.component.html',
})
export class CompanyXLicenseUpdateComponent implements OnInit {
  isSaving = false;
  companyXLicense: ICompanyXLicense | null = null;

  companiesSharedCollection: ICompany[] = [];
  licensesSharedCollection: ILicense[] = [];

  editForm: CompanyXLicenseFormGroup = this.companyXLicenseFormService.createCompanyXLicenseFormGroup();

  constructor(
    protected companyXLicenseService: CompanyXLicenseService,
    protected companyXLicenseFormService: CompanyXLicenseFormService,
    protected companyService: CompanyService,
    protected licenseService: LicenseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareLicense = (o1: ILicense | null, o2: ILicense | null): boolean => this.licenseService.compareLicense(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ companyXLicense }) => {
      this.companyXLicense = companyXLicense;
      if (companyXLicense) {
        this.updateForm(companyXLicense);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const companyXLicense = this.companyXLicenseFormService.getCompanyXLicense(this.editForm);
    if (companyXLicense.id !== null) {
      this.subscribeToSaveResponse(this.companyXLicenseService.update(companyXLicense));
    } else {
      this.subscribeToSaveResponse(this.companyXLicenseService.create(companyXLicense));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompanyXLicense>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(companyXLicense: ICompanyXLicense): void {
    this.companyXLicense = companyXLicense;
    this.companyXLicenseFormService.resetForm(this.editForm, companyXLicense);

    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      companyXLicense.idCompany
    );
    this.licensesSharedCollection = this.licenseService.addLicenseToCollectionIfMissing<ILicense>(
      this.licensesSharedCollection,
      companyXLicense.idLicense
    );
  }

  protected loadRelationshipsOptions(): void {
    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) =>
          this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.companyXLicense?.idCompany)
        )
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));

    this.licenseService
      .query()
      .pipe(map((res: HttpResponse<ILicense[]>) => res.body ?? []))
      .pipe(
        map((licenses: ILicense[]) =>
          this.licenseService.addLicenseToCollectionIfMissing<ILicense>(licenses, this.companyXLicense?.idLicense)
        )
      )
      .subscribe((licenses: ILicense[]) => (this.licensesSharedCollection = licenses));
  }
}

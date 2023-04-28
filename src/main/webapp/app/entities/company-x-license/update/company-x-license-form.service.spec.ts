import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../company-x-license.test-samples';

import { CompanyXLicenseFormService } from './company-x-license-form.service';

describe('CompanyXLicense Form Service', () => {
  let service: CompanyXLicenseFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompanyXLicenseFormService);
  });

  describe('Service methods', () => {
    describe('createCompanyXLicenseFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompanyXLicenseFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });

      it('passing ICompanyXLicense should create a new form with FormGroup', () => {
        const formGroup = service.createCompanyXLicenseFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            idCompany: expect.any(Object),
            idLicense: expect.any(Object),
          })
        );
      });
    });

    describe('getCompanyXLicense', () => {
      it('should return NewCompanyXLicense for default CompanyXLicense initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createCompanyXLicenseFormGroup(sampleWithNewData);

        const companyXLicense = service.getCompanyXLicense(formGroup) as any;

        expect(companyXLicense).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompanyXLicense for empty CompanyXLicense initial value', () => {
        const formGroup = service.createCompanyXLicenseFormGroup();

        const companyXLicense = service.getCompanyXLicense(formGroup) as any;

        expect(companyXLicense).toMatchObject({});
      });

      it('should return ICompanyXLicense', () => {
        const formGroup = service.createCompanyXLicenseFormGroup(sampleWithRequiredData);

        const companyXLicense = service.getCompanyXLicense(formGroup) as any;

        expect(companyXLicense).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompanyXLicense should not enable id FormControl', () => {
        const formGroup = service.createCompanyXLicenseFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompanyXLicense should disable id FormControl', () => {
        const formGroup = service.createCompanyXLicenseFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

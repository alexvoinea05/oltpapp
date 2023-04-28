import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompanyXLicense, NewCompanyXLicense } from '../company-x-license.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompanyXLicense for edit and NewCompanyXLicenseFormGroupInput for create.
 */
type CompanyXLicenseFormGroupInput = ICompanyXLicense | PartialWithRequiredKeyOf<NewCompanyXLicense>;

type CompanyXLicenseFormDefaults = Pick<NewCompanyXLicense, 'id'>;

type CompanyXLicenseFormGroupContent = {
  id: FormControl<ICompanyXLicense['id'] | NewCompanyXLicense['id']>;
  idCompany: FormControl<ICompanyXLicense['idCompany']>;
  idLicense: FormControl<ICompanyXLicense['idLicense']>;
};

export type CompanyXLicenseFormGroup = FormGroup<CompanyXLicenseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompanyXLicenseFormService {
  createCompanyXLicenseFormGroup(companyXLicense: CompanyXLicenseFormGroupInput = { id: null }): CompanyXLicenseFormGroup {
    const companyXLicenseRawValue = {
      ...this.getFormDefaults(),
      ...companyXLicense,
    };
    return new FormGroup<CompanyXLicenseFormGroupContent>({
      id: new FormControl(
        { value: companyXLicenseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      idCompany: new FormControl(companyXLicenseRawValue.idCompany),
      idLicense: new FormControl(companyXLicenseRawValue.idLicense),
    });
  }

  getCompanyXLicense(form: CompanyXLicenseFormGroup): ICompanyXLicense | NewCompanyXLicense {
    return form.getRawValue() as ICompanyXLicense | NewCompanyXLicense;
  }

  resetForm(form: CompanyXLicenseFormGroup, companyXLicense: CompanyXLicenseFormGroupInput): void {
    const companyXLicenseRawValue = { ...this.getFormDefaults(), ...companyXLicense };
    form.reset(
      {
        ...companyXLicenseRawValue,
        id: { value: companyXLicenseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): CompanyXLicenseFormDefaults {
    return {
      id: null,
    };
  }
}

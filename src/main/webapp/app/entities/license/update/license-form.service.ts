import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ILicense, NewLicense } from '../license.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ILicense for edit and NewLicenseFormGroupInput for create.
 */
type LicenseFormGroupInput = ILicense | PartialWithRequiredKeyOf<NewLicense>;

type LicenseFormDefaults = Pick<NewLicense, 'id'>;

type LicenseFormGroupContent = {
  id: FormControl<ILicense['id'] | NewLicense['id']>;
  licenseNumber: FormControl<ILicense['licenseNumber']>;
  licenseDescription: FormControl<ILicense['licenseDescription']>;
};

export type LicenseFormGroup = FormGroup<LicenseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class LicenseFormService {
  createLicenseFormGroup(license: LicenseFormGroupInput = { id: null }): LicenseFormGroup {
    const licenseRawValue = {
      ...this.getFormDefaults(),
      ...license,
    };
    return new FormGroup<LicenseFormGroupContent>({
      id: new FormControl(
        { value: licenseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      licenseNumber: new FormControl(licenseRawValue.licenseNumber, {
        validators: [Validators.required],
      }),
      licenseDescription: new FormControl(licenseRawValue.licenseDescription),
    });
  }

  getLicense(form: LicenseFormGroup): ILicense | NewLicense {
    return form.getRawValue() as ILicense | NewLicense;
  }

  resetForm(form: LicenseFormGroup, license: LicenseFormGroupInput): void {
    const licenseRawValue = { ...this.getFormDefaults(), ...license };
    form.reset(
      {
        ...licenseRawValue,
        id: { value: licenseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): LicenseFormDefaults {
    return {
      id: null,
    };
  }
}

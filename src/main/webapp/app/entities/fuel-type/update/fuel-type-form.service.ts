import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuelType, NewFuelType } from '../fuel-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelType for edit and NewFuelTypeFormGroupInput for create.
 */
type FuelTypeFormGroupInput = IFuelType | PartialWithRequiredKeyOf<NewFuelType>;

type FuelTypeFormDefaults = Pick<NewFuelType, 'id'>;

type FuelTypeFormGroupContent = {
  id: FormControl<IFuelType['id'] | NewFuelType['id']>;
  code: FormControl<IFuelType['code']>;
  description: FormControl<IFuelType['description']>;
};

export type FuelTypeFormGroup = FormGroup<FuelTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTypeFormService {
  createFuelTypeFormGroup(fuelType: FuelTypeFormGroupInput = { id: null }): FuelTypeFormGroup {
    const fuelTypeRawValue = {
      ...this.getFormDefaults(),
      ...fuelType,
    };
    return new FormGroup<FuelTypeFormGroupContent>({
      id: new FormControl(
        { value: fuelTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(fuelTypeRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(fuelTypeRawValue.description),
    });
  }

  getFuelType(form: FuelTypeFormGroup): IFuelType | NewFuelType {
    return form.getRawValue() as IFuelType | NewFuelType;
  }

  resetForm(form: FuelTypeFormGroup, fuelType: FuelTypeFormGroupInput): void {
    const fuelTypeRawValue = { ...this.getFormDefaults(), ...fuelType };
    form.reset(
      {
        ...fuelTypeRawValue,
        id: { value: fuelTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): FuelTypeFormDefaults {
    return {
      id: null,
    };
  }
}

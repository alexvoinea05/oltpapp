import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayType, NewRailwayType } from '../railway-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayType for edit and NewRailwayTypeFormGroupInput for create.
 */
type RailwayTypeFormGroupInput = IRailwayType | PartialWithRequiredKeyOf<NewRailwayType>;

type RailwayTypeFormDefaults = Pick<NewRailwayType, 'id'>;

type RailwayTypeFormGroupContent = {
  id: FormControl<IRailwayType['id'] | NewRailwayType['id']>;
  code: FormControl<IRailwayType['code']>;
  description: FormControl<IRailwayType['description']>;
};

export type RailwayTypeFormGroup = FormGroup<RailwayTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayTypeFormService {
  createRailwayTypeFormGroup(railwayType: RailwayTypeFormGroupInput = { id: null }): RailwayTypeFormGroup {
    const railwayTypeRawValue = {
      ...this.getFormDefaults(),
      ...railwayType,
    };
    return new FormGroup<RailwayTypeFormGroupContent>({
      id: new FormControl(
        { value: railwayTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(railwayTypeRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(railwayTypeRawValue.description),
    });
  }

  getRailwayType(form: RailwayTypeFormGroup): IRailwayType | NewRailwayType {
    return form.getRawValue() as IRailwayType | NewRailwayType;
  }

  resetForm(form: RailwayTypeFormGroup, railwayType: RailwayTypeFormGroupInput): void {
    const railwayTypeRawValue = { ...this.getFormDefaults(), ...railwayType };
    form.reset(
      {
        ...railwayTypeRawValue,
        id: { value: railwayTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayTypeFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IIncidentStatus, NewIncidentStatus } from '../incident-status.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIncidentStatus for edit and NewIncidentStatusFormGroupInput for create.
 */
type IncidentStatusFormGroupInput = IIncidentStatus | PartialWithRequiredKeyOf<NewIncidentStatus>;

type IncidentStatusFormDefaults = Pick<NewIncidentStatus, 'id'>;

type IncidentStatusFormGroupContent = {
  id: FormControl<IIncidentStatus['id'] | NewIncidentStatus['id']>;
  code: FormControl<IIncidentStatus['code']>;
  description: FormControl<IIncidentStatus['description']>;
};

export type IncidentStatusFormGroup = FormGroup<IncidentStatusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IncidentStatusFormService {
  createIncidentStatusFormGroup(incidentStatus: IncidentStatusFormGroupInput = { id: null }): IncidentStatusFormGroup {
    const incidentStatusRawValue = {
      ...this.getFormDefaults(),
      ...incidentStatus,
    };
    return new FormGroup<IncidentStatusFormGroupContent>({
      id: new FormControl(
        { value: incidentStatusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(incidentStatusRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(incidentStatusRawValue.description),
    });
  }

  getIncidentStatus(form: IncidentStatusFormGroup): IIncidentStatus | NewIncidentStatus {
    return form.getRawValue() as IIncidentStatus | NewIncidentStatus;
  }

  resetForm(form: IncidentStatusFormGroup, incidentStatus: IncidentStatusFormGroupInput): void {
    const incidentStatusRawValue = { ...this.getFormDefaults(), ...incidentStatus };
    form.reset(
      {
        ...incidentStatusRawValue,
        id: { value: incidentStatusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IncidentStatusFormDefaults {
    return {
      id: null,
    };
  }
}

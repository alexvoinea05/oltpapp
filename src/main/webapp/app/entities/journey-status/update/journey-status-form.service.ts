import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJourneyStatus, NewJourneyStatus } from '../journey-status.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourneyStatus for edit and NewJourneyStatusFormGroupInput for create.
 */
type JourneyStatusFormGroupInput = IJourneyStatus | PartialWithRequiredKeyOf<NewJourneyStatus>;

type JourneyStatusFormDefaults = Pick<NewJourneyStatus, 'id'>;

type JourneyStatusFormGroupContent = {
  id: FormControl<IJourneyStatus['id'] | NewJourneyStatus['id']>;
  code: FormControl<IJourneyStatus['code']>;
  description: FormControl<IJourneyStatus['description']>;
};

export type JourneyStatusFormGroup = FormGroup<JourneyStatusFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusFormService {
  createJourneyStatusFormGroup(journeyStatus: JourneyStatusFormGroupInput = { id: null }): JourneyStatusFormGroup {
    const journeyStatusRawValue = {
      ...this.getFormDefaults(),
      ...journeyStatus,
    };
    return new FormGroup<JourneyStatusFormGroupContent>({
      id: new FormControl(
        { value: journeyStatusRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(journeyStatusRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(journeyStatusRawValue.description),
    });
  }

  getJourneyStatus(form: JourneyStatusFormGroup): IJourneyStatus | NewJourneyStatus {
    return form.getRawValue() as IJourneyStatus | NewJourneyStatus;
  }

  resetForm(form: JourneyStatusFormGroup, journeyStatus: JourneyStatusFormGroupInput): void {
    const journeyStatusRawValue = { ...this.getFormDefaults(), ...journeyStatus };
    form.reset(
      {
        ...journeyStatusRawValue,
        id: { value: journeyStatusRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyStatusFormDefaults {
    return {
      id: null,
    };
  }
}

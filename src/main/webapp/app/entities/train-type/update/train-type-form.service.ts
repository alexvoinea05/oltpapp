import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrainType, NewTrainType } from '../train-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrainType for edit and NewTrainTypeFormGroupInput for create.
 */
type TrainTypeFormGroupInput = ITrainType | PartialWithRequiredKeyOf<NewTrainType>;

type TrainTypeFormDefaults = Pick<NewTrainType, 'id'>;

type TrainTypeFormGroupContent = {
  id: FormControl<ITrainType['id'] | NewTrainType['id']>;
  code: FormControl<ITrainType['code']>;
  description: FormControl<ITrainType['description']>;
};

export type TrainTypeFormGroup = FormGroup<TrainTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainTypeFormService {
  createTrainTypeFormGroup(trainType: TrainTypeFormGroupInput = { id: null }): TrainTypeFormGroup {
    const trainTypeRawValue = {
      ...this.getFormDefaults(),
      ...trainType,
    };
    return new FormGroup<TrainTypeFormGroupContent>({
      id: new FormControl(
        { value: trainTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainTypeRawValue.code, {
        validators: [Validators.required],
      }),
      description: new FormControl(trainTypeRawValue.description),
    });
  }

  getTrainType(form: TrainTypeFormGroup): ITrainType | NewTrainType {
    return form.getRawValue() as ITrainType | NewTrainType;
  }

  resetForm(form: TrainTypeFormGroup, trainType: TrainTypeFormGroupInput): void {
    const trainTypeRawValue = { ...this.getFormDefaults(), ...trainType };
    form.reset(
      {
        ...trainTypeRawValue,
        id: { value: trainTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainTypeFormDefaults {
    return {
      id: null,
    };
  }
}

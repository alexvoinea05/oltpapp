import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITrain, NewTrain } from '../train.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITrain for edit and NewTrainFormGroupInput for create.
 */
type TrainFormGroupInput = ITrain | PartialWithRequiredKeyOf<NewTrain>;

type TrainFormDefaults = Pick<NewTrain, 'id'>;

type TrainFormGroupContent = {
  id: FormControl<ITrain['id'] | NewTrain['id']>;
  code: FormControl<ITrain['code']>;
  numberOfSeats: FormControl<ITrain['numberOfSeats']>;
  idFuelType: FormControl<ITrain['idFuelType']>;
  idTrainType: FormControl<ITrain['idTrainType']>;
};

export type TrainFormGroup = FormGroup<TrainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TrainFormService {
  createTrainFormGroup(train: TrainFormGroupInput = { id: null }): TrainFormGroup {
    const trainRawValue = {
      ...this.getFormDefaults(),
      ...train,
    };
    return new FormGroup<TrainFormGroupContent>({
      id: new FormControl(
        { value: trainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      code: new FormControl(trainRawValue.code, {
        validators: [Validators.required],
      }),
      numberOfSeats: new FormControl(trainRawValue.numberOfSeats, {
        validators: [Validators.required],
      }),
      idFuelType: new FormControl(trainRawValue.idFuelType),
      idTrainType: new FormControl(trainRawValue.idTrainType),
    });
  }

  getTrain(form: TrainFormGroup): ITrain | NewTrain {
    return form.getRawValue() as ITrain | NewTrain;
  }

  resetForm(form: TrainFormGroup, train: TrainFormGroupInput): void {
    const trainRawValue = { ...this.getFormDefaults(), ...train };
    form.reset(
      {
        ...trainRawValue,
        id: { value: trainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): TrainFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRailwayStation, NewRailwayStation } from '../railway-station.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRailwayStation for edit and NewRailwayStationFormGroupInput for create.
 */
type RailwayStationFormGroupInput = IRailwayStation | PartialWithRequiredKeyOf<NewRailwayStation>;

type RailwayStationFormDefaults = Pick<NewRailwayStation, 'id'>;

type RailwayStationFormGroupContent = {
  id: FormControl<IRailwayStation['id'] | NewRailwayStation['id']>;
  railwayStationName: FormControl<IRailwayStation['railwayStationName']>;
  idRailwayType: FormControl<IRailwayStation['idRailwayType']>;
  idAddress: FormControl<IRailwayStation['idAddress']>;
};

export type RailwayStationFormGroup = FormGroup<RailwayStationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RailwayStationFormService {
  createRailwayStationFormGroup(railwayStation: RailwayStationFormGroupInput = { id: null }): RailwayStationFormGroup {
    const railwayStationRawValue = {
      ...this.getFormDefaults(),
      ...railwayStation,
    };
    return new FormGroup<RailwayStationFormGroupContent>({
      id: new FormControl(
        { value: railwayStationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      railwayStationName: new FormControl(railwayStationRawValue.railwayStationName, {
        validators: [Validators.required],
      }),
      idRailwayType: new FormControl(railwayStationRawValue.idRailwayType),
      idAddress: new FormControl(railwayStationRawValue.idAddress),
    });
  }

  getRailwayStation(form: RailwayStationFormGroup): IRailwayStation | NewRailwayStation {
    return form.getRawValue() as IRailwayStation | NewRailwayStation;
  }

  resetForm(form: RailwayStationFormGroup, railwayStation: RailwayStationFormGroupInput): void {
    const railwayStationRawValue = { ...this.getFormDefaults(), ...railwayStation };
    form.reset(
      {
        ...railwayStationRawValue,
        id: { value: railwayStationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): RailwayStationFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IJourney, NewJourney } from '../journey.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJourney for edit and NewJourneyFormGroupInput for create.
 */
type JourneyFormGroupInput = IJourney | PartialWithRequiredKeyOf<NewJourney>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IJourney | NewJourney> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

type JourneyFormRawValue = FormValueOf<IJourney>;

type NewJourneyFormRawValue = FormValueOf<NewJourney>;

type JourneyFormDefaults = Pick<
  NewJourney,
  'id' | 'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
>;

type JourneyFormGroupContent = {
  id: FormControl<JourneyFormRawValue['id'] | NewJourney['id']>;
  distance: FormControl<JourneyFormRawValue['distance']>;
  journeyDuration: FormControl<JourneyFormRawValue['journeyDuration']>;
  actualDepartureTime: FormControl<JourneyFormRawValue['actualDepartureTime']>;
  plannedDepartureTime: FormControl<JourneyFormRawValue['plannedDepartureTime']>;
  actualArrivalTime: FormControl<JourneyFormRawValue['actualArrivalTime']>;
  plannedArrivalTime: FormControl<JourneyFormRawValue['plannedArrivalTime']>;
  ticketPrice: FormControl<JourneyFormRawValue['ticketPrice']>;
  numberOfStops: FormControl<JourneyFormRawValue['numberOfStops']>;
  timeOfStops: FormControl<JourneyFormRawValue['timeOfStops']>;
  minutesLate: FormControl<JourneyFormRawValue['minutesLate']>;
  idJourneyStatus: FormControl<JourneyFormRawValue['idJourneyStatus']>;
  idTrain: FormControl<JourneyFormRawValue['idTrain']>;
  idCompany: FormControl<JourneyFormRawValue['idCompany']>;
  idRailwayStationDeparture: FormControl<JourneyFormRawValue['idRailwayStationDeparture']>;
  idRailwayStationArrival: FormControl<JourneyFormRawValue['idRailwayStationArrival']>;
};

export type JourneyFormGroup = FormGroup<JourneyFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JourneyFormService {
  createJourneyFormGroup(journey: JourneyFormGroupInput = { id: null }): JourneyFormGroup {
    const journeyRawValue = this.convertJourneyToJourneyRawValue({
      ...this.getFormDefaults(),
      ...journey,
    });
    return new FormGroup<JourneyFormGroupContent>({
      id: new FormControl(
        { value: journeyRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      distance: new FormControl(journeyRawValue.distance, {
        validators: [Validators.required],
      }),
      journeyDuration: new FormControl(journeyRawValue.journeyDuration, {
        validators: [Validators.required],
      }),
      actualDepartureTime: new FormControl(journeyRawValue.actualDepartureTime),
      plannedDepartureTime: new FormControl(journeyRawValue.plannedDepartureTime, {
        validators: [Validators.required],
      }),
      actualArrivalTime: new FormControl(journeyRawValue.actualArrivalTime),
      plannedArrivalTime: new FormControl(journeyRawValue.plannedArrivalTime, {
        validators: [Validators.required],
      }),
      ticketPrice: new FormControl(journeyRawValue.ticketPrice, {
        validators: [Validators.required],
      }),
      numberOfStops: new FormControl(journeyRawValue.numberOfStops),
      timeOfStops: new FormControl(journeyRawValue.timeOfStops, {
        validators: [Validators.required],
      }),
      minutesLate: new FormControl(journeyRawValue.minutesLate, {
        validators: [Validators.required],
      }),
      idJourneyStatus: new FormControl(journeyRawValue.idJourneyStatus),
      idTrain: new FormControl(journeyRawValue.idTrain),
      idCompany: new FormControl(journeyRawValue.idCompany),
      idRailwayStationDeparture: new FormControl(journeyRawValue.idRailwayStationDeparture),
      idRailwayStationArrival: new FormControl(journeyRawValue.idRailwayStationArrival),
    });
  }

  getJourney(form: JourneyFormGroup): IJourney | NewJourney {
    return this.convertJourneyRawValueToJourney(form.getRawValue() as JourneyFormRawValue | NewJourneyFormRawValue);
  }

  resetForm(form: JourneyFormGroup, journey: JourneyFormGroupInput): void {
    const journeyRawValue = this.convertJourneyToJourneyRawValue({ ...this.getFormDefaults(), ...journey });
    form.reset(
      {
        ...journeyRawValue,
        id: { value: journeyRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): JourneyFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      actualDepartureTime: currentTime,
      plannedDepartureTime: currentTime,
      actualArrivalTime: currentTime,
      plannedArrivalTime: currentTime,
    };
  }

  private convertJourneyRawValueToJourney(rawJourney: JourneyFormRawValue | NewJourneyFormRawValue): IJourney | NewJourney {
    return {
      ...rawJourney,
      actualDepartureTime: dayjs(rawJourney.actualDepartureTime, DATE_TIME_FORMAT),
      plannedDepartureTime: dayjs(rawJourney.plannedDepartureTime, DATE_TIME_FORMAT),
      actualArrivalTime: dayjs(rawJourney.actualArrivalTime, DATE_TIME_FORMAT),
      plannedArrivalTime: dayjs(rawJourney.plannedArrivalTime, DATE_TIME_FORMAT),
    };
  }

  private convertJourneyToJourneyRawValue(
    journey: IJourney | (Partial<NewJourney> & JourneyFormDefaults)
  ): JourneyFormRawValue | PartialWithRequiredKeyOf<NewJourneyFormRawValue> {
    return {
      ...journey,
      actualDepartureTime: journey.actualDepartureTime ? journey.actualDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      plannedDepartureTime: journey.plannedDepartureTime ? journey.plannedDepartureTime.format(DATE_TIME_FORMAT) : undefined,
      actualArrivalTime: journey.actualArrivalTime ? journey.actualArrivalTime.format(DATE_TIME_FORMAT) : undefined,
      plannedArrivalTime: journey.plannedArrivalTime ? journey.plannedArrivalTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

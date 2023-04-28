import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IIncident, NewIncident } from '../incident.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IIncident for edit and NewIncidentFormGroupInput for create.
 */
type IncidentFormGroupInput = IIncident | PartialWithRequiredKeyOf<NewIncident>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IIncident | NewIncident> = Omit<T, 'time'> & {
  time?: string | null;
};

type IncidentFormRawValue = FormValueOf<IIncident>;

type NewIncidentFormRawValue = FormValueOf<NewIncident>;

type IncidentFormDefaults = Pick<NewIncident, 'id' | 'time'>;

type IncidentFormGroupContent = {
  id: FormControl<IncidentFormRawValue['id'] | NewIncident['id']>;
  description: FormControl<IncidentFormRawValue['description']>;
  time: FormControl<IncidentFormRawValue['time']>;
  idIncidentStatus: FormControl<IncidentFormRawValue['idIncidentStatus']>;
  idAppUser: FormControl<IncidentFormRawValue['idAppUser']>;
  idJourney: FormControl<IncidentFormRawValue['idJourney']>;
};

export type IncidentFormGroup = FormGroup<IncidentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class IncidentFormService {
  createIncidentFormGroup(incident: IncidentFormGroupInput = { id: null }): IncidentFormGroup {
    const incidentRawValue = this.convertIncidentToIncidentRawValue({
      ...this.getFormDefaults(),
      ...incident,
    });
    return new FormGroup<IncidentFormGroupContent>({
      id: new FormControl(
        { value: incidentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      description: new FormControl(incidentRawValue.description, {
        validators: [Validators.required],
      }),
      time: new FormControl(incidentRawValue.time, {
        validators: [Validators.required],
      }),
      idIncidentStatus: new FormControl(incidentRawValue.idIncidentStatus),
      idAppUser: new FormControl(incidentRawValue.idAppUser),
      idJourney: new FormControl(incidentRawValue.idJourney),
    });
  }

  getIncident(form: IncidentFormGroup): IIncident | NewIncident {
    return this.convertIncidentRawValueToIncident(form.getRawValue() as IncidentFormRawValue | NewIncidentFormRawValue);
  }

  resetForm(form: IncidentFormGroup, incident: IncidentFormGroupInput): void {
    const incidentRawValue = this.convertIncidentToIncidentRawValue({ ...this.getFormDefaults(), ...incident });
    form.reset(
      {
        ...incidentRawValue,
        id: { value: incidentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): IncidentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      time: currentTime,
    };
  }

  private convertIncidentRawValueToIncident(rawIncident: IncidentFormRawValue | NewIncidentFormRawValue): IIncident | NewIncident {
    return {
      ...rawIncident,
      time: dayjs(rawIncident.time, DATE_TIME_FORMAT),
    };
  }

  private convertIncidentToIncidentRawValue(
    incident: IIncident | (Partial<NewIncident> & IncidentFormDefaults)
  ): IncidentFormRawValue | PartialWithRequiredKeyOf<NewIncidentFormRawValue> {
    return {
      ...incident,
      time: incident.time ? incident.time.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

import { IIncidentStatus, NewIncidentStatus } from './incident-status.model';

export const sampleWithRequiredData: IIncidentStatus = {
  id: 68888,
  code: 'state aggregate',
};

export const sampleWithPartialData: IIncidentStatus = {
  id: 79047,
  code: 'Plastic Frozen',
  description: 'navigate Metal Direct',
};

export const sampleWithFullData: IIncidentStatus = {
  id: 2975,
  code: 'Borders',
  description: 'e-services infomediaries Representative',
};

export const sampleWithNewData: NewIncidentStatus = {
  code: 'Table Supervisor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

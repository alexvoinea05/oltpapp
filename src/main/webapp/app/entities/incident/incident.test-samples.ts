import dayjs from 'dayjs/esm';

import { IIncident, NewIncident } from './incident.model';

export const sampleWithRequiredData: IIncident = {
  id: 42575,
  description: 'Jewelery demand-driven niches',
  time: dayjs('2023-01-28T21:33'),
};

export const sampleWithPartialData: IIncident = {
  id: 19536,
  description: 'Kids',
  time: dayjs('2023-01-29T12:39'),
};

export const sampleWithFullData: IIncident = {
  id: 37218,
  description: 'Steel matrix',
  time: dayjs('2023-01-29T15:49'),
};

export const sampleWithNewData: NewIncident = {
  description: 'customized calculate Frozen',
  time: dayjs('2023-01-28T20:48'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

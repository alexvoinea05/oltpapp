import { IJourneyStatus, NewJourneyStatus } from './journey-status.model';

export const sampleWithRequiredData: IJourneyStatus = {
  id: 18238,
  code: 'utilisation user',
};

export const sampleWithPartialData: IJourneyStatus = {
  id: 92649,
  code: 'Chicken indigo matrix',
};

export const sampleWithFullData: IJourneyStatus = {
  id: 83899,
  code: 'Wooden Investment',
  description: 'Savings quantify Avon',
};

export const sampleWithNewData: NewJourneyStatus = {
  code: 'relationships Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

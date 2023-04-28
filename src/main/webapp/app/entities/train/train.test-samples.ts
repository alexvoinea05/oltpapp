import { ITrain, NewTrain } from './train.model';

export const sampleWithRequiredData: ITrain = {
  id: 84948,
  code: 'architect Turnpike Mongolia',
  numberOfSeats: 19569,
};

export const sampleWithPartialData: ITrain = {
  id: 62390,
  code: 'Loan Supervisor Soft',
  numberOfSeats: 48597,
};

export const sampleWithFullData: ITrain = {
  id: 47331,
  code: 'human-resource',
  numberOfSeats: 79495,
};

export const sampleWithNewData: NewTrain = {
  code: 'empower',
  numberOfSeats: 64621,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

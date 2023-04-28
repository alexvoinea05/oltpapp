import { ITrainType, NewTrainType } from './train-type.model';

export const sampleWithRequiredData: ITrainType = {
  id: 42725,
  code: 'connecting Checking Account',
};

export const sampleWithPartialData: ITrainType = {
  id: 68501,
  code: 'SMTP Producer',
  description: 'transmit Unions Integration',
};

export const sampleWithFullData: ITrainType = {
  id: 52516,
  code: 'connecting',
  description: 'Exclusive',
};

export const sampleWithNewData: NewTrainType = {
  code: 'Swaziland Frozen',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ILicense, NewLicense } from './license.model';

export const sampleWithRequiredData: ILicense = {
  id: 86783,
  licenseNumber: 22310,
};

export const sampleWithPartialData: ILicense = {
  id: 11804,
  licenseNumber: 52251,
  licenseDescription: 'navigate Togo Carolina',
};

export const sampleWithFullData: ILicense = {
  id: 42443,
  licenseNumber: 64337,
  licenseDescription: 'Corners transmitting Loan',
};

export const sampleWithNewData: NewLicense = {
  licenseNumber: 48475,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 34483,
  streetNumber: 'wireless Fresh Court',
  street: 'Tommie Street',
};

export const sampleWithFullData: IAddress = {
  id: 34407,
  streetNumber: 'efficient Montana',
  street: 'Marquardt Street',
  zipcode: 'Intelligent Ameliorated',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

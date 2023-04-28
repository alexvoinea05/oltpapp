import { IDistrict, NewDistrict } from './district.model';

export const sampleWithRequiredData: IDistrict = {
  id: 85653,
};

export const sampleWithPartialData: IDistrict = {
  id: 17947,
  name: 'Tuna visualize',
};

export const sampleWithFullData: IDistrict = {
  id: 76539,
  name: 'protocol compressing',
};

export const sampleWithNewData: NewDistrict = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

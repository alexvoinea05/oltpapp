import { IFuelType, NewFuelType } from './fuel-type.model';

export const sampleWithRequiredData: IFuelType = {
  id: 89463,
  code: 'backing initiative deposit',
};

export const sampleWithPartialData: IFuelType = {
  id: 724,
  code: 'Green invoice',
  description: 'Avon',
};

export const sampleWithFullData: IFuelType = {
  id: 58428,
  code: 'Shoes',
  description: 'neutral Rubber Mountain',
};

export const sampleWithNewData: NewFuelType = {
  code: 'benchmark Concrete',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

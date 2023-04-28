import { ICity, NewCity } from './city.model';

export const sampleWithRequiredData: ICity = {
  id: 79135,
};

export const sampleWithPartialData: ICity = {
  id: 84803,
  name: 'Buckinghamshire',
};

export const sampleWithFullData: ICity = {
  id: 48061,
  name: 'Movies',
};

export const sampleWithNewData: NewCity = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

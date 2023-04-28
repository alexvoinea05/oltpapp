import { ICompany, NewCompany } from './company.model';

export const sampleWithRequiredData: ICompany = {
  id: 32440,
  name: 'Djibouti Washington',
  identificationNumber: 'Awesome Avon Avon',
};

export const sampleWithPartialData: ICompany = {
  id: 13981,
  name: 'Maryland Oval',
  identificationNumber: 'Estate',
};

export const sampleWithFullData: ICompany = {
  id: 28107,
  name: 'Mouse',
  identificationNumber: 'Chips Ford',
};

export const sampleWithNewData: NewCompany = {
  name: 'Granite panel',
  identificationNumber: 'Tasty sexy JSON',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ICompanyXLicense, NewCompanyXLicense } from './company-x-license.model';

export const sampleWithRequiredData: ICompanyXLicense = {
  id: 74807,
};

export const sampleWithPartialData: ICompanyXLicense = {
  id: 86615,
};

export const sampleWithFullData: ICompanyXLicense = {
  id: 8488,
};

export const sampleWithNewData: NewCompanyXLicense = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { IUserType, NewUserType } from './user-type.model';

export const sampleWithRequiredData: IUserType = {
  id: 38772,
  code: 'Kentucky',
  discount: 72638,
};

export const sampleWithPartialData: IUserType = {
  id: 6032,
  code: 'initiative invoice',
  discount: 77115,
};

export const sampleWithFullData: IUserType = {
  id: 33343,
  code: 'open-source Oregon',
  discount: 48423,
};

export const sampleWithNewData: NewUserType = {
  code: 'plug-and-play Books',
  discount: 42504,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

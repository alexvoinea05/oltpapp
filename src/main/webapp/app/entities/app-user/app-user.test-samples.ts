import { IAppUser, NewAppUser } from './app-user.model';

export const sampleWithRequiredData: IAppUser = {
  idUser: 1883,
  email: 'Roxanne_Hackett@hotmail.com',
  password: 'Borders mobile',
  lastName: 'Turner',
  firstName: 'Maryam',
};

export const sampleWithPartialData: IAppUser = {
  idUser: 46406,
  email: 'Caleigh.Hand53@hotmail.com',
  password: 'XSS Books',
  lastName: 'Kreiger',
  firstName: 'Pat',
};

export const sampleWithFullData: IAppUser = {
  idUser: 40932,
  email: 'Alyson_King@gmail.com',
  password: 'monitor',
  balance: 44980,
  lastName: 'Mosciski',
  firstName: 'Arvilla',
};

export const sampleWithNewData: NewAppUser = {
  email: 'Quentin_Treutel@gmail.com',
  password: 'applications',
  lastName: 'Farrell',
  firstName: 'Ida',
  idUser: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

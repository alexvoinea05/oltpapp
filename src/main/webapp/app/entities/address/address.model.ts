import { ICity } from 'app/entities/city/city.model';

export interface IAddress {
  id: number;
  streetNumber?: string | null;
  street?: string | null;
  zipcode?: string | null;
  idCity?: Pick<ICity, 'id'> | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };

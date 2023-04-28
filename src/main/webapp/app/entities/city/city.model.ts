import { IDistrict } from 'app/entities/district/district.model';

export interface ICity {
  id: number;
  name?: string | null;
  idDistrict?: Pick<IDistrict, 'id'> | null;
}

export type NewCity = Omit<ICity, 'id'> & { id: null };

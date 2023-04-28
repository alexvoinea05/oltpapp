export interface IFuelType {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewFuelType = Omit<IFuelType, 'id'> & { id: null };

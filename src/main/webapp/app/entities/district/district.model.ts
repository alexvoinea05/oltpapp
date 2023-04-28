export interface IDistrict {
  id: number;
  name?: string | null;
}

export type NewDistrict = Omit<IDistrict, 'id'> & { id: null };

export interface IUserType {
  id: number;
  code?: string | null;
  discount?: number | null;
}

export type NewUserType = Omit<IUserType, 'id'> & { id: null };

export interface ILicense {
  id: number;
  licenseNumber?: number | null;
  licenseDescription?: string | null;
}

export type NewLicense = Omit<ILicense, 'id'> & { id: null };

import { ICompany } from 'app/entities/company/company.model';
import { ILicense } from 'app/entities/license/license.model';

export interface ICompanyXLicense {
  id: number;
  idCompany?: Pick<ICompany, 'id'> | null;
  idLicense?: Pick<ILicense, 'id'> | null;
}

export type NewCompanyXLicense = Omit<ICompanyXLicense, 'id'> & { id: null };

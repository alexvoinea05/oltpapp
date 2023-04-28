import { IUser } from 'app/entities/user/user.model';
import { IUserType } from 'app/entities/user-type/user-type.model';

export interface IAppUser {
  idUser: number;
  email?: string | null;
  password?: string | null;
  balance?: number | null;
  lastName?: string | null;
  firstName?: string | null;
  user?: Pick<IUser, 'id'> | null;
  idUserType?: Pick<IUserType, 'id'> | null;
}

export type NewAppUser = Omit<IAppUser, 'idUser'> & { idUser: null };

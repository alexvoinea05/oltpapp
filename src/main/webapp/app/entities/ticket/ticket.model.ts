import dayjs from 'dayjs/esm';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { IJourney } from 'app/entities/journey/journey.model';

export interface ITicket {
  id: number;
  finalPrice?: number | null;
  quantity?: number | null;
  time?: dayjs.Dayjs | null;
  idAppUser?: Pick<IAppUser, 'idUser'> | null;
  idJourney?: Pick<IJourney, 'id'> | null;
}

export type NewTicket = Omit<ITicket, 'id'> & { id: null };

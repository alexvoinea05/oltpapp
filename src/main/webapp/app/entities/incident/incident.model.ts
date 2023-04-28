import dayjs from 'dayjs/esm';
import { IIncidentStatus } from 'app/entities/incident-status/incident-status.model';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { IJourney } from 'app/entities/journey/journey.model';

export interface IIncident {
  id: number;
  description?: string | null;
  time?: dayjs.Dayjs | null;
  idIncidentStatus?: Pick<IIncidentStatus, 'id'> | null;
  idAppUser?: Pick<IAppUser, 'idUser'> | null;
  idJourney?: Pick<IJourney, 'id'> | null;
}

export type NewIncident = Omit<IIncident, 'id'> & { id: null };

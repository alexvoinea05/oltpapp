import dayjs from 'dayjs/esm';
import { IJourneyStatus } from 'app/entities/journey-status/journey-status.model';
import { ITrain } from 'app/entities/train/train.model';
import { ICompany } from 'app/entities/company/company.model';
import { IRailwayStation } from 'app/entities/railway-station/railway-station.model';

export interface IJourney {
  id: number;
  distance?: number | null;
  journeyDuration?: number | null;
  actualDepartureTime?: dayjs.Dayjs | null;
  plannedDepartureTime?: dayjs.Dayjs | null;
  actualArrivalTime?: dayjs.Dayjs | null;
  plannedArrivalTime?: dayjs.Dayjs | null;
  ticketPrice?: number | null;
  numberOfStops?: number | null;
  timeOfStops?: number | null;
  minutesLate?: number | null;
  idJourneyStatus?: Pick<IJourneyStatus, 'id'> | null;
  idTrain?: Pick<ITrain, 'id'> | null;
  idCompany?: Pick<ICompany, 'id'> | null;
  idRailwayStationDeparture?: Pick<IRailwayStation, 'id'> | null;
  idRailwayStationArrival?: Pick<IRailwayStation, 'id'> | null;
}

export type NewJourney = Omit<IJourney, 'id'> & { id: null };

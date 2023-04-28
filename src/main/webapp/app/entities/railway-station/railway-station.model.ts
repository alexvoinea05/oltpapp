import { IRailwayType } from 'app/entities/railway-type/railway-type.model';
import { IAddress } from 'app/entities/address/address.model';

export interface IRailwayStation {
  id: number;
  railwayStationName?: string | null;
  idRailwayType?: Pick<IRailwayType, 'id'> | null;
  idAddress?: Pick<IAddress, 'id'> | null;
}

export type NewRailwayStation = Omit<IRailwayStation, 'id'> & { id: null };

import { IFuelType } from 'app/entities/fuel-type/fuel-type.model';
import { ITrainType } from 'app/entities/train-type/train-type.model';

export interface ITrain {
  id: number;
  code?: string | null;
  numberOfSeats?: number | null;
  idFuelType?: Pick<IFuelType, 'id'> | null;
  idTrainType?: Pick<ITrainType, 'id'> | null;
}

export type NewTrain = Omit<ITrain, 'id'> & { id: null };

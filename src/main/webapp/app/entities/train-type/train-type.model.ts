export interface ITrainType {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewTrainType = Omit<ITrainType, 'id'> & { id: null };

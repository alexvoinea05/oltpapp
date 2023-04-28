export interface IRailwayType {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewRailwayType = Omit<IRailwayType, 'id'> & { id: null };

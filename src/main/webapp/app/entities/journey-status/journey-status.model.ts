export interface IJourneyStatus {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewJourneyStatus = Omit<IJourneyStatus, 'id'> & { id: null };

export interface IIncidentStatus {
  id: number;
  code?: string | null;
  description?: string | null;
}

export type NewIncidentStatus = Omit<IIncidentStatus, 'id'> & { id: null };

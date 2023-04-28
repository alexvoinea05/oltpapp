import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IIncidentStatus, NewIncidentStatus } from '../incident-status.model';

export type PartialUpdateIncidentStatus = Partial<IIncidentStatus> & Pick<IIncidentStatus, 'id'>;

export type EntityResponseType = HttpResponse<IIncidentStatus>;
export type EntityArrayResponseType = HttpResponse<IIncidentStatus[]>;

@Injectable({ providedIn: 'root' })
export class IncidentStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/incident-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(incidentStatus: NewIncidentStatus): Observable<EntityResponseType> {
    return this.http.post<IIncidentStatus>(this.resourceUrl, incidentStatus, { observe: 'response' });
  }

  update(incidentStatus: IIncidentStatus): Observable<EntityResponseType> {
    return this.http.put<IIncidentStatus>(`${this.resourceUrl}/${this.getIncidentStatusIdentifier(incidentStatus)}`, incidentStatus, {
      observe: 'response',
    });
  }

  partialUpdate(incidentStatus: PartialUpdateIncidentStatus): Observable<EntityResponseType> {
    return this.http.patch<IIncidentStatus>(`${this.resourceUrl}/${this.getIncidentStatusIdentifier(incidentStatus)}`, incidentStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IIncidentStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IIncidentStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getIncidentStatusIdentifier(incidentStatus: Pick<IIncidentStatus, 'id'>): number {
    return incidentStatus.id;
  }

  compareIncidentStatus(o1: Pick<IIncidentStatus, 'id'> | null, o2: Pick<IIncidentStatus, 'id'> | null): boolean {
    return o1 && o2 ? this.getIncidentStatusIdentifier(o1) === this.getIncidentStatusIdentifier(o2) : o1 === o2;
  }

  addIncidentStatusToCollectionIfMissing<Type extends Pick<IIncidentStatus, 'id'>>(
    incidentStatusCollection: Type[],
    ...incidentStatusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const incidentStatuses: Type[] = incidentStatusesToCheck.filter(isPresent);
    if (incidentStatuses.length > 0) {
      const incidentStatusCollectionIdentifiers = incidentStatusCollection.map(
        incidentStatusItem => this.getIncidentStatusIdentifier(incidentStatusItem)!
      );
      const incidentStatusesToAdd = incidentStatuses.filter(incidentStatusItem => {
        const incidentStatusIdentifier = this.getIncidentStatusIdentifier(incidentStatusItem);
        if (incidentStatusCollectionIdentifiers.includes(incidentStatusIdentifier)) {
          return false;
        }
        incidentStatusCollectionIdentifiers.push(incidentStatusIdentifier);
        return true;
      });
      return [...incidentStatusesToAdd, ...incidentStatusCollection];
    }
    return incidentStatusCollection;
  }
}

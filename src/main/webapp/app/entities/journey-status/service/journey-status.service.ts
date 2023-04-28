import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourneyStatus, NewJourneyStatus } from '../journey-status.model';

export type PartialUpdateJourneyStatus = Partial<IJourneyStatus> & Pick<IJourneyStatus, 'id'>;

export type EntityResponseType = HttpResponse<IJourneyStatus>;
export type EntityArrayResponseType = HttpResponse<IJourneyStatus[]>;

@Injectable({ providedIn: 'root' })
export class JourneyStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journey-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journeyStatus: NewJourneyStatus): Observable<EntityResponseType> {
    return this.http.post<IJourneyStatus>(this.resourceUrl, journeyStatus, { observe: 'response' });
  }

  update(journeyStatus: IJourneyStatus): Observable<EntityResponseType> {
    return this.http.put<IJourneyStatus>(`${this.resourceUrl}/${this.getJourneyStatusIdentifier(journeyStatus)}`, journeyStatus, {
      observe: 'response',
    });
  }

  partialUpdate(journeyStatus: PartialUpdateJourneyStatus): Observable<EntityResponseType> {
    return this.http.patch<IJourneyStatus>(`${this.resourceUrl}/${this.getJourneyStatusIdentifier(journeyStatus)}`, journeyStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJourneyStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJourneyStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyStatusIdentifier(journeyStatus: Pick<IJourneyStatus, 'id'>): number {
    return journeyStatus.id;
  }

  compareJourneyStatus(o1: Pick<IJourneyStatus, 'id'> | null, o2: Pick<IJourneyStatus, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyStatusIdentifier(o1) === this.getJourneyStatusIdentifier(o2) : o1 === o2;
  }

  addJourneyStatusToCollectionIfMissing<Type extends Pick<IJourneyStatus, 'id'>>(
    journeyStatusCollection: Type[],
    ...journeyStatusesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeyStatuses: Type[] = journeyStatusesToCheck.filter(isPresent);
    if (journeyStatuses.length > 0) {
      const journeyStatusCollectionIdentifiers = journeyStatusCollection.map(
        journeyStatusItem => this.getJourneyStatusIdentifier(journeyStatusItem)!
      );
      const journeyStatusesToAdd = journeyStatuses.filter(journeyStatusItem => {
        const journeyStatusIdentifier = this.getJourneyStatusIdentifier(journeyStatusItem);
        if (journeyStatusCollectionIdentifiers.includes(journeyStatusIdentifier)) {
          return false;
        }
        journeyStatusCollectionIdentifiers.push(journeyStatusIdentifier);
        return true;
      });
      return [...journeyStatusesToAdd, ...journeyStatusCollection];
    }
    return journeyStatusCollection;
  }
}

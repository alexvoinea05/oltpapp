import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJourney, NewJourney } from '../journey.model';

export type PartialUpdateJourney = Partial<IJourney> & Pick<IJourney, 'id'>;

type RestOf<T extends IJourney | NewJourney> = Omit<
  T,
  'actualDepartureTime' | 'plannedDepartureTime' | 'actualArrivalTime' | 'plannedArrivalTime'
> & {
  actualDepartureTime?: string | null;
  plannedDepartureTime?: string | null;
  actualArrivalTime?: string | null;
  plannedArrivalTime?: string | null;
};

export type RestJourney = RestOf<IJourney>;

export type NewRestJourney = RestOf<NewJourney>;

export type PartialUpdateRestJourney = RestOf<PartialUpdateJourney>;

export type EntityResponseType = HttpResponse<IJourney>;
export type EntityArrayResponseType = HttpResponse<IJourney[]>;

@Injectable({ providedIn: 'root' })
export class JourneyService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/journeys');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(journey: NewJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journey);
    return this.http
      .post<RestJourney>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(journey: IJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journey);
    return this.http
      .put<RestJourney>(`${this.resourceUrl}/${this.getJourneyIdentifier(journey)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(journey: PartialUpdateJourney): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(journey);
    return this.http
      .patch<RestJourney>(`${this.resourceUrl}/${this.getJourneyIdentifier(journey)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJourney>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJourney[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJourneyIdentifier(journey: Pick<IJourney, 'id'>): number {
    return journey.id;
  }

  compareJourney(o1: Pick<IJourney, 'id'> | null, o2: Pick<IJourney, 'id'> | null): boolean {
    return o1 && o2 ? this.getJourneyIdentifier(o1) === this.getJourneyIdentifier(o2) : o1 === o2;
  }

  addJourneyToCollectionIfMissing<Type extends Pick<IJourney, 'id'>>(
    journeyCollection: Type[],
    ...journeysToCheck: (Type | null | undefined)[]
  ): Type[] {
    const journeys: Type[] = journeysToCheck.filter(isPresent);
    if (journeys.length > 0) {
      const journeyCollectionIdentifiers = journeyCollection.map(journeyItem => this.getJourneyIdentifier(journeyItem)!);
      const journeysToAdd = journeys.filter(journeyItem => {
        const journeyIdentifier = this.getJourneyIdentifier(journeyItem);
        if (journeyCollectionIdentifiers.includes(journeyIdentifier)) {
          return false;
        }
        journeyCollectionIdentifiers.push(journeyIdentifier);
        return true;
      });
      return [...journeysToAdd, ...journeyCollection];
    }
    return journeyCollection;
  }

  protected convertDateFromClient<T extends IJourney | NewJourney | PartialUpdateJourney>(journey: T): RestOf<T> {
    return {
      ...journey,
      actualDepartureTime: journey.actualDepartureTime?.toJSON() ?? null,
      plannedDepartureTime: journey.plannedDepartureTime?.toJSON() ?? null,
      actualArrivalTime: journey.actualArrivalTime?.toJSON() ?? null,
      plannedArrivalTime: journey.plannedArrivalTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restJourney: RestJourney): IJourney {
    return {
      ...restJourney,
      actualDepartureTime: restJourney.actualDepartureTime ? dayjs(restJourney.actualDepartureTime) : undefined,
      plannedDepartureTime: restJourney.plannedDepartureTime ? dayjs(restJourney.plannedDepartureTime) : undefined,
      actualArrivalTime: restJourney.actualArrivalTime ? dayjs(restJourney.actualArrivalTime) : undefined,
      plannedArrivalTime: restJourney.plannedArrivalTime ? dayjs(restJourney.plannedArrivalTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJourney>): HttpResponse<IJourney> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJourney[]>): HttpResponse<IJourney[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

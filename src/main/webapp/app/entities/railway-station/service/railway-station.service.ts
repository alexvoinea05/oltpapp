import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRailwayStation, NewRailwayStation } from '../railway-station.model';

export type PartialUpdateRailwayStation = Partial<IRailwayStation> & Pick<IRailwayStation, 'id'>;

export type EntityResponseType = HttpResponse<IRailwayStation>;
export type EntityArrayResponseType = HttpResponse<IRailwayStation[]>;

@Injectable({ providedIn: 'root' })
export class RailwayStationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/railway-stations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(railwayStation: NewRailwayStation): Observable<EntityResponseType> {
    return this.http.post<IRailwayStation>(this.resourceUrl, railwayStation, { observe: 'response' });
  }

  update(railwayStation: IRailwayStation): Observable<EntityResponseType> {
    return this.http.put<IRailwayStation>(`${this.resourceUrl}/${this.getRailwayStationIdentifier(railwayStation)}`, railwayStation, {
      observe: 'response',
    });
  }

  partialUpdate(railwayStation: PartialUpdateRailwayStation): Observable<EntityResponseType> {
    return this.http.patch<IRailwayStation>(`${this.resourceUrl}/${this.getRailwayStationIdentifier(railwayStation)}`, railwayStation, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRailwayStation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRailwayStation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRailwayStationIdentifier(railwayStation: Pick<IRailwayStation, 'id'>): number {
    return railwayStation.id;
  }

  compareRailwayStation(o1: Pick<IRailwayStation, 'id'> | null, o2: Pick<IRailwayStation, 'id'> | null): boolean {
    return o1 && o2 ? this.getRailwayStationIdentifier(o1) === this.getRailwayStationIdentifier(o2) : o1 === o2;
  }

  addRailwayStationToCollectionIfMissing<Type extends Pick<IRailwayStation, 'id'>>(
    railwayStationCollection: Type[],
    ...railwayStationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const railwayStations: Type[] = railwayStationsToCheck.filter(isPresent);
    if (railwayStations.length > 0) {
      const railwayStationCollectionIdentifiers = railwayStationCollection.map(
        railwayStationItem => this.getRailwayStationIdentifier(railwayStationItem)!
      );
      const railwayStationsToAdd = railwayStations.filter(railwayStationItem => {
        const railwayStationIdentifier = this.getRailwayStationIdentifier(railwayStationItem);
        if (railwayStationCollectionIdentifiers.includes(railwayStationIdentifier)) {
          return false;
        }
        railwayStationCollectionIdentifiers.push(railwayStationIdentifier);
        return true;
      });
      return [...railwayStationsToAdd, ...railwayStationCollection];
    }
    return railwayStationCollection;
  }
}

import { IRailwayStation, NewRailwayStation } from './railway-station.model';

export const sampleWithRequiredData: IRailwayStation = {
  id: 57181,
  railwayStationName: 'Horizontal',
};

export const sampleWithPartialData: IRailwayStation = {
  id: 5780,
  railwayStationName: 'Generic Ball sensor',
};

export const sampleWithFullData: IRailwayStation = {
  id: 22554,
  railwayStationName: 'Intranet',
};

export const sampleWithNewData: NewRailwayStation = {
  railwayStationName: 'Personal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

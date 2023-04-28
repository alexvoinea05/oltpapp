import { IRailwayType, NewRailwayType } from './railway-type.model';

export const sampleWithRequiredData: IRailwayType = {
  id: 79308,
  code: 'Coordinator',
};

export const sampleWithPartialData: IRailwayType = {
  id: 26653,
  code: 'Investor Incredible',
  description: 'Arizona Falls',
};

export const sampleWithFullData: IRailwayType = {
  id: 19614,
  code: 'Compatible incubate',
  description: 'Fantastic Stand-alone',
};

export const sampleWithNewData: NewRailwayType = {
  code: 'mindshare',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

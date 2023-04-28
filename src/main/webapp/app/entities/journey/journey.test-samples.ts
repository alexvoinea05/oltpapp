import dayjs from 'dayjs/esm';

import { IJourney, NewJourney } from './journey.model';

export const sampleWithRequiredData: IJourney = {
  id: 87932,
  distance: 46833,
  journeyDuration: 1297,
  plannedDepartureTime: dayjs('2023-01-29T14:50'),
  plannedArrivalTime: dayjs('2023-01-29T08:28'),
  ticketPrice: 88448,
  timeOfStops: 73737,
  minutesLate: 90140,
};

export const sampleWithPartialData: IJourney = {
  id: 47273,
  distance: 79411,
  journeyDuration: 52278,
  actualDepartureTime: dayjs('2023-01-29T13:54'),
  plannedDepartureTime: dayjs('2023-01-29T11:57'),
  plannedArrivalTime: dayjs('2023-01-28T20:13'),
  ticketPrice: 49975,
  numberOfStops: 89266,
  timeOfStops: 17000,
  minutesLate: 21061,
};

export const sampleWithFullData: IJourney = {
  id: 61472,
  distance: 42021,
  journeyDuration: 86092,
  actualDepartureTime: dayjs('2023-01-29T17:30'),
  plannedDepartureTime: dayjs('2023-01-29T03:57'),
  actualArrivalTime: dayjs('2023-01-29T09:41'),
  plannedArrivalTime: dayjs('2023-01-29T12:54'),
  ticketPrice: 68418,
  numberOfStops: 10403,
  timeOfStops: 44631,
  minutesLate: 35008,
};

export const sampleWithNewData: NewJourney = {
  distance: 40630,
  journeyDuration: 69940,
  plannedDepartureTime: dayjs('2023-01-29T11:09'),
  plannedArrivalTime: dayjs('2023-01-28T22:44'),
  ticketPrice: 1982,
  timeOfStops: 64584,
  minutesLate: 57934,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

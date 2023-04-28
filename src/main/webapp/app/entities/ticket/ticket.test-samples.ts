import dayjs from 'dayjs/esm';

import { ITicket, NewTicket } from './ticket.model';

export const sampleWithRequiredData: ITicket = {
  id: 65582,
  finalPrice: 75353,
  quantity: 9016,
  time: dayjs('2023-01-29T08:30'),
};

export const sampleWithPartialData: ITicket = {
  id: 93545,
  finalPrice: 23104,
  quantity: 7730,
  time: dayjs('2023-01-29T19:42'),
};

export const sampleWithFullData: ITicket = {
  id: 4280,
  finalPrice: 9940,
  quantity: 77434,
  time: dayjs('2023-01-29T02:20'),
};

export const sampleWithNewData: NewTicket = {
  finalPrice: 94764,
  quantity: 48085,
  time: dayjs('2023-01-29T16:36'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

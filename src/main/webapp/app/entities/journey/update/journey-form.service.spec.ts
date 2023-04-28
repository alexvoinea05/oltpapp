import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey.test-samples';

import { JourneyFormService } from './journey-form.service';

describe('Journey Form Service', () => {
  let service: JourneyFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            idJourneyStatus: expect.any(Object),
            idTrain: expect.any(Object),
            idCompany: expect.any(Object),
            idRailwayStationDeparture: expect.any(Object),
            idRailwayStationArrival: expect.any(Object),
          })
        );
      });

      it('passing IJourney should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            distance: expect.any(Object),
            journeyDuration: expect.any(Object),
            actualDepartureTime: expect.any(Object),
            plannedDepartureTime: expect.any(Object),
            actualArrivalTime: expect.any(Object),
            plannedArrivalTime: expect.any(Object),
            ticketPrice: expect.any(Object),
            numberOfStops: expect.any(Object),
            timeOfStops: expect.any(Object),
            minutesLate: expect.any(Object),
            idJourneyStatus: expect.any(Object),
            idTrain: expect.any(Object),
            idCompany: expect.any(Object),
            idRailwayStationDeparture: expect.any(Object),
            idRailwayStationArrival: expect.any(Object),
          })
        );
      });
    });

    describe('getJourney', () => {
      it('should return NewJourney for default Journey initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyFormGroup(sampleWithNewData);

        const journey = service.getJourney(formGroup) as any;

        expect(journey).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourney for empty Journey initial value', () => {
        const formGroup = service.createJourneyFormGroup();

        const journey = service.getJourney(formGroup) as any;

        expect(journey).toMatchObject({});
      });

      it('should return IJourney', () => {
        const formGroup = service.createJourneyFormGroup(sampleWithRequiredData);

        const journey = service.getJourney(formGroup) as any;

        expect(journey).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourney should not enable id FormControl', () => {
        const formGroup = service.createJourneyFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourney should disable id FormControl', () => {
        const formGroup = service.createJourneyFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

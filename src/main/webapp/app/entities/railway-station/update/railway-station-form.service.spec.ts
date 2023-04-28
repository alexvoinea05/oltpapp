import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-station.test-samples';

import { RailwayStationFormService } from './railway-station-form.service';

describe('RailwayStation Form Service', () => {
  let service: RailwayStationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayStationFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayStationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayStationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            idRailwayType: expect.any(Object),
            idAddress: expect.any(Object),
          })
        );
      });

      it('passing IRailwayStation should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayStationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            railwayStationName: expect.any(Object),
            idRailwayType: expect.any(Object),
            idAddress: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayStation', () => {
      it('should return NewRailwayStation for default RailwayStation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayStationFormGroup(sampleWithNewData);

        const railwayStation = service.getRailwayStation(formGroup) as any;

        expect(railwayStation).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayStation for empty RailwayStation initial value', () => {
        const formGroup = service.createRailwayStationFormGroup();

        const railwayStation = service.getRailwayStation(formGroup) as any;

        expect(railwayStation).toMatchObject({});
      });

      it('should return IRailwayStation', () => {
        const formGroup = service.createRailwayStationFormGroup(sampleWithRequiredData);

        const railwayStation = service.getRailwayStation(formGroup) as any;

        expect(railwayStation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayStation should not enable id FormControl', () => {
        const formGroup = service.createRailwayStationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayStation should disable id FormControl', () => {
        const formGroup = service.createRailwayStationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

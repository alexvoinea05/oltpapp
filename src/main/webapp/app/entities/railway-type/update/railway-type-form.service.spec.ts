import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../railway-type.test-samples';

import { RailwayTypeFormService } from './railway-type-form.service';

describe('RailwayType Form Service', () => {
  let service: RailwayTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RailwayTypeFormService);
  });

  describe('Service methods', () => {
    describe('createRailwayTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createRailwayTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IRailwayType should create a new form with FormGroup', () => {
        const formGroup = service.createRailwayTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getRailwayType', () => {
      it('should return NewRailwayType for default RailwayType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createRailwayTypeFormGroup(sampleWithNewData);

        const railwayType = service.getRailwayType(formGroup) as any;

        expect(railwayType).toMatchObject(sampleWithNewData);
      });

      it('should return NewRailwayType for empty RailwayType initial value', () => {
        const formGroup = service.createRailwayTypeFormGroup();

        const railwayType = service.getRailwayType(formGroup) as any;

        expect(railwayType).toMatchObject({});
      });

      it('should return IRailwayType', () => {
        const formGroup = service.createRailwayTypeFormGroup(sampleWithRequiredData);

        const railwayType = service.getRailwayType(formGroup) as any;

        expect(railwayType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IRailwayType should not enable id FormControl', () => {
        const formGroup = service.createRailwayTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewRailwayType should disable id FormControl', () => {
        const formGroup = service.createRailwayTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

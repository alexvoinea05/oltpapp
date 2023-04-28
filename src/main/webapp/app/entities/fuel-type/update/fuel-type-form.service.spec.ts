import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fuel-type.test-samples';

import { FuelTypeFormService } from './fuel-type-form.service';

describe('FuelType Form Service', () => {
  let service: FuelTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IFuelType should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getFuelType', () => {
      it('should return NewFuelType for default FuelType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createFuelTypeFormGroup(sampleWithNewData);

        const fuelType = service.getFuelType(formGroup) as any;

        expect(fuelType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelType for empty FuelType initial value', () => {
        const formGroup = service.createFuelTypeFormGroup();

        const fuelType = service.getFuelType(formGroup) as any;

        expect(fuelType).toMatchObject({});
      });

      it('should return IFuelType', () => {
        const formGroup = service.createFuelTypeFormGroup(sampleWithRequiredData);

        const fuelType = service.getFuelType(formGroup) as any;

        expect(fuelType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelType should not enable id FormControl', () => {
        const formGroup = service.createFuelTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelType should disable id FormControl', () => {
        const formGroup = service.createFuelTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

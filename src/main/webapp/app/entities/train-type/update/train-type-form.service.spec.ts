import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../train-type.test-samples';

import { TrainTypeFormService } from './train-type-form.service';

describe('TrainType Form Service', () => {
  let service: TrainTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TrainTypeFormService);
  });

  describe('Service methods', () => {
    describe('createTrainTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTrainTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing ITrainType should create a new form with FormGroup', () => {
        const formGroup = service.createTrainTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getTrainType', () => {
      it('should return NewTrainType for default TrainType initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTrainTypeFormGroup(sampleWithNewData);

        const trainType = service.getTrainType(formGroup) as any;

        expect(trainType).toMatchObject(sampleWithNewData);
      });

      it('should return NewTrainType for empty TrainType initial value', () => {
        const formGroup = service.createTrainTypeFormGroup();

        const trainType = service.getTrainType(formGroup) as any;

        expect(trainType).toMatchObject({});
      });

      it('should return ITrainType', () => {
        const formGroup = service.createTrainTypeFormGroup(sampleWithRequiredData);

        const trainType = service.getTrainType(formGroup) as any;

        expect(trainType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITrainType should not enable id FormControl', () => {
        const formGroup = service.createTrainTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTrainType should disable id FormControl', () => {
        const formGroup = service.createTrainTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

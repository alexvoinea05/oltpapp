import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../journey-status.test-samples';

import { JourneyStatusFormService } from './journey-status-form.service';

describe('JourneyStatus Form Service', () => {
  let service: JourneyStatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JourneyStatusFormService);
  });

  describe('Service methods', () => {
    describe('createJourneyStatusFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJourneyStatusFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IJourneyStatus should create a new form with FormGroup', () => {
        const formGroup = service.createJourneyStatusFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getJourneyStatus', () => {
      it('should return NewJourneyStatus for default JourneyStatus initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createJourneyStatusFormGroup(sampleWithNewData);

        const journeyStatus = service.getJourneyStatus(formGroup) as any;

        expect(journeyStatus).toMatchObject(sampleWithNewData);
      });

      it('should return NewJourneyStatus for empty JourneyStatus initial value', () => {
        const formGroup = service.createJourneyStatusFormGroup();

        const journeyStatus = service.getJourneyStatus(formGroup) as any;

        expect(journeyStatus).toMatchObject({});
      });

      it('should return IJourneyStatus', () => {
        const formGroup = service.createJourneyStatusFormGroup(sampleWithRequiredData);

        const journeyStatus = service.getJourneyStatus(formGroup) as any;

        expect(journeyStatus).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJourneyStatus should not enable id FormControl', () => {
        const formGroup = service.createJourneyStatusFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJourneyStatus should disable id FormControl', () => {
        const formGroup = service.createJourneyStatusFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

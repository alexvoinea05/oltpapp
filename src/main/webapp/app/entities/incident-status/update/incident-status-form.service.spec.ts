import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../incident-status.test-samples';

import { IncidentStatusFormService } from './incident-status-form.service';

describe('IncidentStatus Form Service', () => {
  let service: IncidentStatusFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IncidentStatusFormService);
  });

  describe('Service methods', () => {
    describe('createIncidentStatusFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIncidentStatusFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });

      it('passing IIncidentStatus should create a new form with FormGroup', () => {
        const formGroup = service.createIncidentStatusFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            code: expect.any(Object),
            description: expect.any(Object),
          })
        );
      });
    });

    describe('getIncidentStatus', () => {
      it('should return NewIncidentStatus for default IncidentStatus initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIncidentStatusFormGroup(sampleWithNewData);

        const incidentStatus = service.getIncidentStatus(formGroup) as any;

        expect(incidentStatus).toMatchObject(sampleWithNewData);
      });

      it('should return NewIncidentStatus for empty IncidentStatus initial value', () => {
        const formGroup = service.createIncidentStatusFormGroup();

        const incidentStatus = service.getIncidentStatus(formGroup) as any;

        expect(incidentStatus).toMatchObject({});
      });

      it('should return IIncidentStatus', () => {
        const formGroup = service.createIncidentStatusFormGroup(sampleWithRequiredData);

        const incidentStatus = service.getIncidentStatus(formGroup) as any;

        expect(incidentStatus).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIncidentStatus should not enable id FormControl', () => {
        const formGroup = service.createIncidentStatusFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIncidentStatus should disable id FormControl', () => {
        const formGroup = service.createIncidentStatusFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

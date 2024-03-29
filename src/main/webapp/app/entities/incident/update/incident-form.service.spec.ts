import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../incident.test-samples';

import { IncidentFormService } from './incident-form.service';

describe('Incident Form Service', () => {
  let service: IncidentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IncidentFormService);
  });

  describe('Service methods', () => {
    describe('createIncidentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createIncidentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            time: expect.any(Object),
            idIncidentStatus: expect.any(Object),
            idAppUser: expect.any(Object),
            idJourney: expect.any(Object),
          })
        );
      });

      it('passing IIncident should create a new form with FormGroup', () => {
        const formGroup = service.createIncidentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            description: expect.any(Object),
            time: expect.any(Object),
            idIncidentStatus: expect.any(Object),
            idAppUser: expect.any(Object),
            idJourney: expect.any(Object),
          })
        );
      });
    });

    describe('getIncident', () => {
      it('should return NewIncident for default Incident initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createIncidentFormGroup(sampleWithNewData);

        const incident = service.getIncident(formGroup) as any;

        expect(incident).toMatchObject(sampleWithNewData);
      });

      it('should return NewIncident for empty Incident initial value', () => {
        const formGroup = service.createIncidentFormGroup();

        const incident = service.getIncident(formGroup) as any;

        expect(incident).toMatchObject({});
      });

      it('should return IIncident', () => {
        const formGroup = service.createIncidentFormGroup(sampleWithRequiredData);

        const incident = service.getIncident(formGroup) as any;

        expect(incident).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IIncident should not enable id FormControl', () => {
        const formGroup = service.createIncidentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewIncident should disable id FormControl', () => {
        const formGroup = service.createIncidentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

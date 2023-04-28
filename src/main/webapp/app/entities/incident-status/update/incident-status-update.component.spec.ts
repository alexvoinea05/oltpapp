import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IncidentStatusFormService } from './incident-status-form.service';
import { IncidentStatusService } from '../service/incident-status.service';
import { IIncidentStatus } from '../incident-status.model';

import { IncidentStatusUpdateComponent } from './incident-status-update.component';

describe('IncidentStatus Management Update Component', () => {
  let comp: IncidentStatusUpdateComponent;
  let fixture: ComponentFixture<IncidentStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let incidentStatusFormService: IncidentStatusFormService;
  let incidentStatusService: IncidentStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IncidentStatusUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(IncidentStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IncidentStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    incidentStatusFormService = TestBed.inject(IncidentStatusFormService);
    incidentStatusService = TestBed.inject(IncidentStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const incidentStatus: IIncidentStatus = { id: 456 };

      activatedRoute.data = of({ incidentStatus });
      comp.ngOnInit();

      expect(comp.incidentStatus).toEqual(incidentStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncidentStatus>>();
      const incidentStatus = { id: 123 };
      jest.spyOn(incidentStatusFormService, 'getIncidentStatus').mockReturnValue(incidentStatus);
      jest.spyOn(incidentStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incidentStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incidentStatus }));
      saveSubject.complete();

      // THEN
      expect(incidentStatusFormService.getIncidentStatus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(incidentStatusService.update).toHaveBeenCalledWith(expect.objectContaining(incidentStatus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncidentStatus>>();
      const incidentStatus = { id: 123 };
      jest.spyOn(incidentStatusFormService, 'getIncidentStatus').mockReturnValue({ id: null });
      jest.spyOn(incidentStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incidentStatus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incidentStatus }));
      saveSubject.complete();

      // THEN
      expect(incidentStatusFormService.getIncidentStatus).toHaveBeenCalled();
      expect(incidentStatusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncidentStatus>>();
      const incidentStatus = { id: 123 };
      jest.spyOn(incidentStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incidentStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(incidentStatusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyStatusFormService } from './journey-status-form.service';
import { JourneyStatusService } from '../service/journey-status.service';
import { IJourneyStatus } from '../journey-status.model';

import { JourneyStatusUpdateComponent } from './journey-status-update.component';

describe('JourneyStatus Management Update Component', () => {
  let comp: JourneyStatusUpdateComponent;
  let fixture: ComponentFixture<JourneyStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyStatusFormService: JourneyStatusFormService;
  let journeyStatusService: JourneyStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyStatusUpdateComponent],
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
      .overrideTemplate(JourneyStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyStatusFormService = TestBed.inject(JourneyStatusFormService);
    journeyStatusService = TestBed.inject(JourneyStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const journeyStatus: IJourneyStatus = { id: 456 };

      activatedRoute.data = of({ journeyStatus });
      comp.ngOnInit();

      expect(comp.journeyStatus).toEqual(journeyStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatus>>();
      const journeyStatus = { id: 123 };
      jest.spyOn(journeyStatusFormService, 'getJourneyStatus').mockReturnValue(journeyStatus);
      jest.spyOn(journeyStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatus }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusFormService.getJourneyStatus).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyStatusService.update).toHaveBeenCalledWith(expect.objectContaining(journeyStatus));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatus>>();
      const journeyStatus = { id: 123 };
      jest.spyOn(journeyStatusFormService, 'getJourneyStatus').mockReturnValue({ id: null });
      jest.spyOn(journeyStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatus: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journeyStatus }));
      saveSubject.complete();

      // THEN
      expect(journeyStatusFormService.getJourneyStatus).toHaveBeenCalled();
      expect(journeyStatusService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourneyStatus>>();
      const journeyStatus = { id: 123 };
      jest.spyOn(journeyStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journeyStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyStatusService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

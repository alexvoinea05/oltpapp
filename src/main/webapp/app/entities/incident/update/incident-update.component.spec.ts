import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IncidentFormService } from './incident-form.service';
import { IncidentService } from '../service/incident.service';
import { IIncident } from '../incident.model';
import { IIncidentStatus } from 'app/entities/incident-status/incident-status.model';
import { IncidentStatusService } from 'app/entities/incident-status/service/incident-status.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { IJourney } from 'app/entities/journey/journey.model';
import { JourneyService } from 'app/entities/journey/service/journey.service';

import { IncidentUpdateComponent } from './incident-update.component';

describe('Incident Management Update Component', () => {
  let comp: IncidentUpdateComponent;
  let fixture: ComponentFixture<IncidentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let incidentFormService: IncidentFormService;
  let incidentService: IncidentService;
  let incidentStatusService: IncidentStatusService;
  let appUserService: AppUserService;
  let journeyService: JourneyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [IncidentUpdateComponent],
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
      .overrideTemplate(IncidentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(IncidentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    incidentFormService = TestBed.inject(IncidentFormService);
    incidentService = TestBed.inject(IncidentService);
    incidentStatusService = TestBed.inject(IncidentStatusService);
    appUserService = TestBed.inject(AppUserService);
    journeyService = TestBed.inject(JourneyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call IncidentStatus query and add missing value', () => {
      const incident: IIncident = { id: 456 };
      const idIncidentStatus: IIncidentStatus = { id: 76549 };
      incident.idIncidentStatus = idIncidentStatus;

      const incidentStatusCollection: IIncidentStatus[] = [{ id: 70332 }];
      jest.spyOn(incidentStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: incidentStatusCollection })));
      const additionalIncidentStatuses = [idIncidentStatus];
      const expectedCollection: IIncidentStatus[] = [...additionalIncidentStatuses, ...incidentStatusCollection];
      jest.spyOn(incidentStatusService, 'addIncidentStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      expect(incidentStatusService.query).toHaveBeenCalled();
      expect(incidentStatusService.addIncidentStatusToCollectionIfMissing).toHaveBeenCalledWith(
        incidentStatusCollection,
        ...additionalIncidentStatuses.map(expect.objectContaining)
      );
      expect(comp.incidentStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call AppUser query and add missing value', () => {
      const incident: IIncident = { id: 456 };
      const idAppUser: IAppUser = { idUser: 43467 };
      incident.idAppUser = idAppUser;

      const appUserCollection: IAppUser[] = [{ idUser: 67236 }];
      jest.spyOn(appUserService, 'query').mockReturnValue(of(new HttpResponse({ body: appUserCollection })));
      const additionalAppUsers = [idAppUser];
      const expectedCollection: IAppUser[] = [...additionalAppUsers, ...appUserCollection];
      jest.spyOn(appUserService, 'addAppUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      expect(appUserService.query).toHaveBeenCalled();
      expect(appUserService.addAppUserToCollectionIfMissing).toHaveBeenCalledWith(
        appUserCollection,
        ...additionalAppUsers.map(expect.objectContaining)
      );
      expect(comp.appUsersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Journey query and add missing value', () => {
      const incident: IIncident = { id: 456 };
      const idJourney: IJourney = { id: 83720 };
      incident.idJourney = idJourney;

      const journeyCollection: IJourney[] = [{ id: 16625 }];
      jest.spyOn(journeyService, 'query').mockReturnValue(of(new HttpResponse({ body: journeyCollection })));
      const additionalJourneys = [idJourney];
      const expectedCollection: IJourney[] = [...additionalJourneys, ...journeyCollection];
      jest.spyOn(journeyService, 'addJourneyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      expect(journeyService.query).toHaveBeenCalled();
      expect(journeyService.addJourneyToCollectionIfMissing).toHaveBeenCalledWith(
        journeyCollection,
        ...additionalJourneys.map(expect.objectContaining)
      );
      expect(comp.journeysSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const incident: IIncident = { id: 456 };
      const idIncidentStatus: IIncidentStatus = { id: 59815 };
      incident.idIncidentStatus = idIncidentStatus;
      const idAppUser: IAppUser = { idUser: 78166 };
      incident.idAppUser = idAppUser;
      const idJourney: IJourney = { id: 14935 };
      incident.idJourney = idJourney;

      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      expect(comp.incidentStatusesSharedCollection).toContain(idIncidentStatus);
      expect(comp.appUsersSharedCollection).toContain(idAppUser);
      expect(comp.journeysSharedCollection).toContain(idJourney);
      expect(comp.incident).toEqual(incident);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncident>>();
      const incident = { id: 123 };
      jest.spyOn(incidentFormService, 'getIncident').mockReturnValue(incident);
      jest.spyOn(incidentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incident }));
      saveSubject.complete();

      // THEN
      expect(incidentFormService.getIncident).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(incidentService.update).toHaveBeenCalledWith(expect.objectContaining(incident));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncident>>();
      const incident = { id: 123 };
      jest.spyOn(incidentFormService, 'getIncident').mockReturnValue({ id: null });
      jest.spyOn(incidentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incident: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: incident }));
      saveSubject.complete();

      // THEN
      expect(incidentFormService.getIncident).toHaveBeenCalled();
      expect(incidentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IIncident>>();
      const incident = { id: 123 };
      jest.spyOn(incidentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ incident });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(incidentService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareIncidentStatus', () => {
      it('Should forward to incidentStatusService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(incidentStatusService, 'compareIncidentStatus');
        comp.compareIncidentStatus(entity, entity2);
        expect(incidentStatusService.compareIncidentStatus).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAppUser', () => {
      it('Should forward to appUserService', () => {
        const entity = { idUser: 123 };
        const entity2 = { idUser: 456 };
        jest.spyOn(appUserService, 'compareAppUser');
        comp.compareAppUser(entity, entity2);
        expect(appUserService.compareAppUser).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareJourney', () => {
      it('Should forward to journeyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(journeyService, 'compareJourney');
        comp.compareJourney(entity, entity2);
        expect(journeyService.compareJourney).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

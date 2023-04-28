import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { JourneyFormService } from './journey-form.service';
import { JourneyService } from '../service/journey.service';
import { IJourney } from '../journey.model';
import { IJourneyStatus } from 'app/entities/journey-status/journey-status.model';
import { JourneyStatusService } from 'app/entities/journey-status/service/journey-status.service';
import { ITrain } from 'app/entities/train/train.model';
import { TrainService } from 'app/entities/train/service/train.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IRailwayStation } from 'app/entities/railway-station/railway-station.model';
import { RailwayStationService } from 'app/entities/railway-station/service/railway-station.service';

import { JourneyUpdateComponent } from './journey-update.component';

describe('Journey Management Update Component', () => {
  let comp: JourneyUpdateComponent;
  let fixture: ComponentFixture<JourneyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let journeyFormService: JourneyFormService;
  let journeyService: JourneyService;
  let journeyStatusService: JourneyStatusService;
  let trainService: TrainService;
  let companyService: CompanyService;
  let railwayStationService: RailwayStationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [JourneyUpdateComponent],
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
      .overrideTemplate(JourneyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JourneyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    journeyFormService = TestBed.inject(JourneyFormService);
    journeyService = TestBed.inject(JourneyService);
    journeyStatusService = TestBed.inject(JourneyStatusService);
    trainService = TestBed.inject(TrainService);
    companyService = TestBed.inject(CompanyService);
    railwayStationService = TestBed.inject(RailwayStationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call JourneyStatus query and add missing value', () => {
      const journey: IJourney = { id: 456 };
      const idJourneyStatus: IJourneyStatus = { id: 11627 };
      journey.idJourneyStatus = idJourneyStatus;

      const journeyStatusCollection: IJourneyStatus[] = [{ id: 43139 }];
      jest.spyOn(journeyStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: journeyStatusCollection })));
      const additionalJourneyStatuses = [idJourneyStatus];
      const expectedCollection: IJourneyStatus[] = [...additionalJourneyStatuses, ...journeyStatusCollection];
      jest.spyOn(journeyStatusService, 'addJourneyStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      expect(journeyStatusService.query).toHaveBeenCalled();
      expect(journeyStatusService.addJourneyStatusToCollectionIfMissing).toHaveBeenCalledWith(
        journeyStatusCollection,
        ...additionalJourneyStatuses.map(expect.objectContaining)
      );
      expect(comp.journeyStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Train query and add missing value', () => {
      const journey: IJourney = { id: 456 };
      const idTrain: ITrain = { id: 78925 };
      journey.idTrain = idTrain;

      const trainCollection: ITrain[] = [{ id: 10818 }];
      jest.spyOn(trainService, 'query').mockReturnValue(of(new HttpResponse({ body: trainCollection })));
      const additionalTrains = [idTrain];
      const expectedCollection: ITrain[] = [...additionalTrains, ...trainCollection];
      jest.spyOn(trainService, 'addTrainToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      expect(trainService.query).toHaveBeenCalled();
      expect(trainService.addTrainToCollectionIfMissing).toHaveBeenCalledWith(
        trainCollection,
        ...additionalTrains.map(expect.objectContaining)
      );
      expect(comp.trainsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const journey: IJourney = { id: 456 };
      const idCompany: ICompany = { id: 37918 };
      journey.idCompany = idCompany;

      const companyCollection: ICompany[] = [{ id: 61553 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [idCompany];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call RailwayStation query and add missing value', () => {
      const journey: IJourney = { id: 456 };
      const idRailwayStationDeparture: IRailwayStation = { id: 92033 };
      journey.idRailwayStationDeparture = idRailwayStationDeparture;
      const idRailwayStationArrival: IRailwayStation = { id: 63110 };
      journey.idRailwayStationArrival = idRailwayStationArrival;

      const railwayStationCollection: IRailwayStation[] = [{ id: 27372 }];
      jest.spyOn(railwayStationService, 'query').mockReturnValue(of(new HttpResponse({ body: railwayStationCollection })));
      const additionalRailwayStations = [idRailwayStationDeparture, idRailwayStationArrival];
      const expectedCollection: IRailwayStation[] = [...additionalRailwayStations, ...railwayStationCollection];
      jest.spyOn(railwayStationService, 'addRailwayStationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      expect(railwayStationService.query).toHaveBeenCalled();
      expect(railwayStationService.addRailwayStationToCollectionIfMissing).toHaveBeenCalledWith(
        railwayStationCollection,
        ...additionalRailwayStations.map(expect.objectContaining)
      );
      expect(comp.railwayStationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const journey: IJourney = { id: 456 };
      const idJourneyStatus: IJourneyStatus = { id: 99521 };
      journey.idJourneyStatus = idJourneyStatus;
      const idTrain: ITrain = { id: 47500 };
      journey.idTrain = idTrain;
      const idCompany: ICompany = { id: 51005 };
      journey.idCompany = idCompany;
      const idRailwayStationDeparture: IRailwayStation = { id: 30291 };
      journey.idRailwayStationDeparture = idRailwayStationDeparture;
      const idRailwayStationArrival: IRailwayStation = { id: 9567 };
      journey.idRailwayStationArrival = idRailwayStationArrival;

      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      expect(comp.journeyStatusesSharedCollection).toContain(idJourneyStatus);
      expect(comp.trainsSharedCollection).toContain(idTrain);
      expect(comp.companiesSharedCollection).toContain(idCompany);
      expect(comp.railwayStationsSharedCollection).toContain(idRailwayStationDeparture);
      expect(comp.railwayStationsSharedCollection).toContain(idRailwayStationArrival);
      expect(comp.journey).toEqual(journey);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourney>>();
      const journey = { id: 123 };
      jest.spyOn(journeyFormService, 'getJourney').mockReturnValue(journey);
      jest.spyOn(journeyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journey }));
      saveSubject.complete();

      // THEN
      expect(journeyFormService.getJourney).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(journeyService.update).toHaveBeenCalledWith(expect.objectContaining(journey));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourney>>();
      const journey = { id: 123 };
      jest.spyOn(journeyFormService, 'getJourney').mockReturnValue({ id: null });
      jest.spyOn(journeyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journey: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: journey }));
      saveSubject.complete();

      // THEN
      expect(journeyFormService.getJourney).toHaveBeenCalled();
      expect(journeyService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJourney>>();
      const journey = { id: 123 };
      jest.spyOn(journeyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ journey });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(journeyService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareJourneyStatus', () => {
      it('Should forward to journeyStatusService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(journeyStatusService, 'compareJourneyStatus');
        comp.compareJourneyStatus(entity, entity2);
        expect(journeyStatusService.compareJourneyStatus).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTrain', () => {
      it('Should forward to trainService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(trainService, 'compareTrain');
        comp.compareTrain(entity, entity2);
        expect(trainService.compareTrain).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRailwayStation', () => {
      it('Should forward to railwayStationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(railwayStationService, 'compareRailwayStation');
        comp.compareRailwayStation(entity, entity2);
        expect(railwayStationService.compareRailwayStation).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

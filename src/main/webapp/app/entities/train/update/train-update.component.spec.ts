import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainFormService } from './train-form.service';
import { TrainService } from '../service/train.service';
import { ITrain } from '../train.model';
import { IFuelType } from 'app/entities/fuel-type/fuel-type.model';
import { FuelTypeService } from 'app/entities/fuel-type/service/fuel-type.service';
import { ITrainType } from 'app/entities/train-type/train-type.model';
import { TrainTypeService } from 'app/entities/train-type/service/train-type.service';

import { TrainUpdateComponent } from './train-update.component';

describe('Train Management Update Component', () => {
  let comp: TrainUpdateComponent;
  let fixture: ComponentFixture<TrainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainFormService: TrainFormService;
  let trainService: TrainService;
  let fuelTypeService: FuelTypeService;
  let trainTypeService: TrainTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainUpdateComponent],
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
      .overrideTemplate(TrainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainFormService = TestBed.inject(TrainFormService);
    trainService = TestBed.inject(TrainService);
    fuelTypeService = TestBed.inject(FuelTypeService);
    trainTypeService = TestBed.inject(TrainTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call FuelType query and add missing value', () => {
      const train: ITrain = { id: 456 };
      const idFuelType: IFuelType = { id: 34144 };
      train.idFuelType = idFuelType;

      const fuelTypeCollection: IFuelType[] = [{ id: 36068 }];
      jest.spyOn(fuelTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: fuelTypeCollection })));
      const additionalFuelTypes = [idFuelType];
      const expectedCollection: IFuelType[] = [...additionalFuelTypes, ...fuelTypeCollection];
      jest.spyOn(fuelTypeService, 'addFuelTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ train });
      comp.ngOnInit();

      expect(fuelTypeService.query).toHaveBeenCalled();
      expect(fuelTypeService.addFuelTypeToCollectionIfMissing).toHaveBeenCalledWith(
        fuelTypeCollection,
        ...additionalFuelTypes.map(expect.objectContaining)
      );
      expect(comp.fuelTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TrainType query and add missing value', () => {
      const train: ITrain = { id: 456 };
      const idTrainType: ITrainType = { id: 95373 };
      train.idTrainType = idTrainType;

      const trainTypeCollection: ITrainType[] = [{ id: 87632 }];
      jest.spyOn(trainTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: trainTypeCollection })));
      const additionalTrainTypes = [idTrainType];
      const expectedCollection: ITrainType[] = [...additionalTrainTypes, ...trainTypeCollection];
      jest.spyOn(trainTypeService, 'addTrainTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ train });
      comp.ngOnInit();

      expect(trainTypeService.query).toHaveBeenCalled();
      expect(trainTypeService.addTrainTypeToCollectionIfMissing).toHaveBeenCalledWith(
        trainTypeCollection,
        ...additionalTrainTypes.map(expect.objectContaining)
      );
      expect(comp.trainTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const train: ITrain = { id: 456 };
      const idFuelType: IFuelType = { id: 15963 };
      train.idFuelType = idFuelType;
      const idTrainType: ITrainType = { id: 35689 };
      train.idTrainType = idTrainType;

      activatedRoute.data = of({ train });
      comp.ngOnInit();

      expect(comp.fuelTypesSharedCollection).toContain(idFuelType);
      expect(comp.trainTypesSharedCollection).toContain(idTrainType);
      expect(comp.train).toEqual(train);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrain>>();
      const train = { id: 123 };
      jest.spyOn(trainFormService, 'getTrain').mockReturnValue(train);
      jest.spyOn(trainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ train });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: train }));
      saveSubject.complete();

      // THEN
      expect(trainFormService.getTrain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainService.update).toHaveBeenCalledWith(expect.objectContaining(train));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrain>>();
      const train = { id: 123 };
      jest.spyOn(trainFormService, 'getTrain').mockReturnValue({ id: null });
      jest.spyOn(trainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ train: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: train }));
      saveSubject.complete();

      // THEN
      expect(trainFormService.getTrain).toHaveBeenCalled();
      expect(trainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrain>>();
      const train = { id: 123 };
      jest.spyOn(trainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ train });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareFuelType', () => {
      it('Should forward to fuelTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fuelTypeService, 'compareFuelType');
        comp.compareFuelType(entity, entity2);
        expect(fuelTypeService.compareFuelType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareTrainType', () => {
      it('Should forward to trainTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(trainTypeService, 'compareTrainType');
        comp.compareTrainType(entity, entity2);
        expect(trainTypeService.compareTrainType).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

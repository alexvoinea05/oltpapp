import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TrainTypeFormService } from './train-type-form.service';
import { TrainTypeService } from '../service/train-type.service';
import { ITrainType } from '../train-type.model';

import { TrainTypeUpdateComponent } from './train-type-update.component';

describe('TrainType Management Update Component', () => {
  let comp: TrainTypeUpdateComponent;
  let fixture: ComponentFixture<TrainTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let trainTypeFormService: TrainTypeFormService;
  let trainTypeService: TrainTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TrainTypeUpdateComponent],
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
      .overrideTemplate(TrainTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TrainTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    trainTypeFormService = TestBed.inject(TrainTypeFormService);
    trainTypeService = TestBed.inject(TrainTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const trainType: ITrainType = { id: 456 };

      activatedRoute.data = of({ trainType });
      comp.ngOnInit();

      expect(comp.trainType).toEqual(trainType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainType>>();
      const trainType = { id: 123 };
      jest.spyOn(trainTypeFormService, 'getTrainType').mockReturnValue(trainType);
      jest.spyOn(trainTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainType }));
      saveSubject.complete();

      // THEN
      expect(trainTypeFormService.getTrainType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(trainTypeService.update).toHaveBeenCalledWith(expect.objectContaining(trainType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainType>>();
      const trainType = { id: 123 };
      jest.spyOn(trainTypeFormService, 'getTrainType').mockReturnValue({ id: null });
      jest.spyOn(trainTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: trainType }));
      saveSubject.complete();

      // THEN
      expect(trainTypeFormService.getTrainType).toHaveBeenCalled();
      expect(trainTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITrainType>>();
      const trainType = { id: 123 };
      jest.spyOn(trainTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ trainType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(trainTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

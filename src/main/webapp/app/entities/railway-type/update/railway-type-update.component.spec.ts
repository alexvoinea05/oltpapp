import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayTypeFormService } from './railway-type-form.service';
import { RailwayTypeService } from '../service/railway-type.service';
import { IRailwayType } from '../railway-type.model';

import { RailwayTypeUpdateComponent } from './railway-type-update.component';

describe('RailwayType Management Update Component', () => {
  let comp: RailwayTypeUpdateComponent;
  let fixture: ComponentFixture<RailwayTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayTypeFormService: RailwayTypeFormService;
  let railwayTypeService: RailwayTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayTypeUpdateComponent],
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
      .overrideTemplate(RailwayTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayTypeFormService = TestBed.inject(RailwayTypeFormService);
    railwayTypeService = TestBed.inject(RailwayTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const railwayType: IRailwayType = { id: 456 };

      activatedRoute.data = of({ railwayType });
      comp.ngOnInit();

      expect(comp.railwayType).toEqual(railwayType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayType>>();
      const railwayType = { id: 123 };
      jest.spyOn(railwayTypeFormService, 'getRailwayType').mockReturnValue(railwayType);
      jest.spyOn(railwayTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayType }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeFormService.getRailwayType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayTypeService.update).toHaveBeenCalledWith(expect.objectContaining(railwayType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayType>>();
      const railwayType = { id: 123 };
      jest.spyOn(railwayTypeFormService, 'getRailwayType').mockReturnValue({ id: null });
      jest.spyOn(railwayTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayType }));
      saveSubject.complete();

      // THEN
      expect(railwayTypeFormService.getRailwayType).toHaveBeenCalled();
      expect(railwayTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayType>>();
      const railwayType = { id: 123 };
      jest.spyOn(railwayTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

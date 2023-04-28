import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { FuelTypeFormService } from './fuel-type-form.service';
import { FuelTypeService } from '../service/fuel-type.service';
import { IFuelType } from '../fuel-type.model';

import { FuelTypeUpdateComponent } from './fuel-type-update.component';

describe('FuelType Management Update Component', () => {
  let comp: FuelTypeUpdateComponent;
  let fixture: ComponentFixture<FuelTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTypeFormService: FuelTypeFormService;
  let fuelTypeService: FuelTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [FuelTypeUpdateComponent],
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
      .overrideTemplate(FuelTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTypeFormService = TestBed.inject(FuelTypeFormService);
    fuelTypeService = TestBed.inject(FuelTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const fuelType: IFuelType = { id: 456 };

      activatedRoute.data = of({ fuelType });
      comp.ngOnInit();

      expect(comp.fuelType).toEqual(fuelType);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelType>>();
      const fuelType = { id: 123 };
      jest.spyOn(fuelTypeFormService, 'getFuelType').mockReturnValue(fuelType);
      jest.spyOn(fuelTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelType }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeFormService.getFuelType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTypeService.update).toHaveBeenCalledWith(expect.objectContaining(fuelType));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelType>>();
      const fuelType = { id: 123 };
      jest.spyOn(fuelTypeFormService, 'getFuelType').mockReturnValue({ id: null });
      jest.spyOn(fuelTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelType }));
      saveSubject.complete();

      // THEN
      expect(fuelTypeFormService.getFuelType).toHaveBeenCalled();
      expect(fuelTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelType>>();
      const fuelType = { id: 123 };
      jest.spyOn(fuelTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

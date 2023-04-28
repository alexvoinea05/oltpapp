import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { LicenseFormService } from './license-form.service';
import { LicenseService } from '../service/license.service';
import { ILicense } from '../license.model';

import { LicenseUpdateComponent } from './license-update.component';

describe('License Management Update Component', () => {
  let comp: LicenseUpdateComponent;
  let fixture: ComponentFixture<LicenseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let licenseFormService: LicenseFormService;
  let licenseService: LicenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [LicenseUpdateComponent],
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
      .overrideTemplate(LicenseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LicenseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    licenseFormService = TestBed.inject(LicenseFormService);
    licenseService = TestBed.inject(LicenseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const license: ILicense = { id: 456 };

      activatedRoute.data = of({ license });
      comp.ngOnInit();

      expect(comp.license).toEqual(license);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicense>>();
      const license = { id: 123 };
      jest.spyOn(licenseFormService, 'getLicense').mockReturnValue(license);
      jest.spyOn(licenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ license });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: license }));
      saveSubject.complete();

      // THEN
      expect(licenseFormService.getLicense).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(licenseService.update).toHaveBeenCalledWith(expect.objectContaining(license));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicense>>();
      const license = { id: 123 };
      jest.spyOn(licenseFormService, 'getLicense').mockReturnValue({ id: null });
      jest.spyOn(licenseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ license: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: license }));
      saveSubject.complete();

      // THEN
      expect(licenseFormService.getLicense).toHaveBeenCalled();
      expect(licenseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ILicense>>();
      const license = { id: 123 };
      jest.spyOn(licenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ license });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(licenseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

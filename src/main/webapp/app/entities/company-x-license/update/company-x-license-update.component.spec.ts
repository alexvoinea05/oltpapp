import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyXLicenseFormService } from './company-x-license-form.service';
import { CompanyXLicenseService } from '../service/company-x-license.service';
import { ICompanyXLicense } from '../company-x-license.model';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ILicense } from 'app/entities/license/license.model';
import { LicenseService } from 'app/entities/license/service/license.service';

import { CompanyXLicenseUpdateComponent } from './company-x-license-update.component';

describe('CompanyXLicense Management Update Component', () => {
  let comp: CompanyXLicenseUpdateComponent;
  let fixture: ComponentFixture<CompanyXLicenseUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyXLicenseFormService: CompanyXLicenseFormService;
  let companyXLicenseService: CompanyXLicenseService;
  let companyService: CompanyService;
  let licenseService: LicenseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyXLicenseUpdateComponent],
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
      .overrideTemplate(CompanyXLicenseUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyXLicenseUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyXLicenseFormService = TestBed.inject(CompanyXLicenseFormService);
    companyXLicenseService = TestBed.inject(CompanyXLicenseService);
    companyService = TestBed.inject(CompanyService);
    licenseService = TestBed.inject(LicenseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Company query and add missing value', () => {
      const companyXLicense: ICompanyXLicense = { id: 456 };
      const idCompany: ICompany = { id: 44031 };
      companyXLicense.idCompany = idCompany;

      const companyCollection: ICompany[] = [{ id: 55093 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [idCompany];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyXLicense });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(
        companyCollection,
        ...additionalCompanies.map(expect.objectContaining)
      );
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call License query and add missing value', () => {
      const companyXLicense: ICompanyXLicense = { id: 456 };
      const idLicense: ILicense = { id: 32726 };
      companyXLicense.idLicense = idLicense;

      const licenseCollection: ILicense[] = [{ id: 60171 }];
      jest.spyOn(licenseService, 'query').mockReturnValue(of(new HttpResponse({ body: licenseCollection })));
      const additionalLicenses = [idLicense];
      const expectedCollection: ILicense[] = [...additionalLicenses, ...licenseCollection];
      jest.spyOn(licenseService, 'addLicenseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ companyXLicense });
      comp.ngOnInit();

      expect(licenseService.query).toHaveBeenCalled();
      expect(licenseService.addLicenseToCollectionIfMissing).toHaveBeenCalledWith(
        licenseCollection,
        ...additionalLicenses.map(expect.objectContaining)
      );
      expect(comp.licensesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const companyXLicense: ICompanyXLicense = { id: 456 };
      const idCompany: ICompany = { id: 14914 };
      companyXLicense.idCompany = idCompany;
      const idLicense: ILicense = { id: 41103 };
      companyXLicense.idLicense = idLicense;

      activatedRoute.data = of({ companyXLicense });
      comp.ngOnInit();

      expect(comp.companiesSharedCollection).toContain(idCompany);
      expect(comp.licensesSharedCollection).toContain(idLicense);
      expect(comp.companyXLicense).toEqual(companyXLicense);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyXLicense>>();
      const companyXLicense = { id: 123 };
      jest.spyOn(companyXLicenseFormService, 'getCompanyXLicense').mockReturnValue(companyXLicense);
      jest.spyOn(companyXLicenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyXLicense });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyXLicense }));
      saveSubject.complete();

      // THEN
      expect(companyXLicenseFormService.getCompanyXLicense).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyXLicenseService.update).toHaveBeenCalledWith(expect.objectContaining(companyXLicense));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyXLicense>>();
      const companyXLicense = { id: 123 };
      jest.spyOn(companyXLicenseFormService, 'getCompanyXLicense').mockReturnValue({ id: null });
      jest.spyOn(companyXLicenseService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyXLicense: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: companyXLicense }));
      saveSubject.complete();

      // THEN
      expect(companyXLicenseFormService.getCompanyXLicense).toHaveBeenCalled();
      expect(companyXLicenseService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICompanyXLicense>>();
      const companyXLicense = { id: 123 };
      jest.spyOn(companyXLicenseService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ companyXLicense });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyXLicenseService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCompany', () => {
      it('Should forward to companyService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(companyService, 'compareCompany');
        comp.compareCompany(entity, entity2);
        expect(companyService.compareCompany).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareLicense', () => {
      it('Should forward to licenseService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(licenseService, 'compareLicense');
        comp.compareLicense(entity, entity2);
        expect(licenseService.compareLicense).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

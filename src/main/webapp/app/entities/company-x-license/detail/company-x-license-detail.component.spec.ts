import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CompanyXLicenseDetailComponent } from './company-x-license-detail.component';

describe('CompanyXLicense Management Detail Component', () => {
  let comp: CompanyXLicenseDetailComponent;
  let fixture: ComponentFixture<CompanyXLicenseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyXLicenseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ companyXLicense: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CompanyXLicenseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CompanyXLicenseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load companyXLicense on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.companyXLicense).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

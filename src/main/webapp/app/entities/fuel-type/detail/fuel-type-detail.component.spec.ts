import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FuelTypeDetailComponent } from './fuel-type-detail.component';

describe('FuelType Management Detail Component', () => {
  let comp: FuelTypeDetailComponent;
  let fixture: ComponentFixture<FuelTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FuelTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ fuelType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(FuelTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FuelTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load fuelType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.fuelType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

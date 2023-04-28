import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayTypeDetailComponent } from './railway-type-detail.component';

describe('RailwayType Management Detail Component', () => {
  let comp: RailwayTypeDetailComponent;
  let fixture: ComponentFixture<RailwayTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

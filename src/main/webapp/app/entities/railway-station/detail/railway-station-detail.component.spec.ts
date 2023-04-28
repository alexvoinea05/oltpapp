import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RailwayStationDetailComponent } from './railway-station-detail.component';

describe('RailwayStation Management Detail Component', () => {
  let comp: RailwayStationDetailComponent;
  let fixture: ComponentFixture<RailwayStationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RailwayStationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ railwayStation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RailwayStationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RailwayStationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load railwayStation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.railwayStation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

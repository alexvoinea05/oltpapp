import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainTypeDetailComponent } from './train-type-detail.component';

describe('TrainType Management Detail Component', () => {
  let comp: TrainTypeDetailComponent;
  let fixture: ComponentFixture<TrainTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ trainType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load trainType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.trainType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

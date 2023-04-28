import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TrainDetailComponent } from './train-detail.component';

describe('Train Management Detail Component', () => {
  let comp: TrainDetailComponent;
  let fixture: ComponentFixture<TrainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TrainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ train: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TrainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TrainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load train on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.train).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

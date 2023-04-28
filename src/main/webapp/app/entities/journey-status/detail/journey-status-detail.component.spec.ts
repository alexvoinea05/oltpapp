import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JourneyStatusDetailComponent } from './journey-status-detail.component';

describe('JourneyStatus Management Detail Component', () => {
  let comp: JourneyStatusDetailComponent;
  let fixture: ComponentFixture<JourneyStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JourneyStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ journeyStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(JourneyStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(JourneyStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load journeyStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.journeyStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

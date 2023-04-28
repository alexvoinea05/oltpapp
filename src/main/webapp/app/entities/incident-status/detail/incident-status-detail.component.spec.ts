import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IncidentStatusDetailComponent } from './incident-status-detail.component';

describe('IncidentStatus Management Detail Component', () => {
  let comp: IncidentStatusDetailComponent;
  let fixture: ComponentFixture<IncidentStatusDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IncidentStatusDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ incidentStatus: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IncidentStatusDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IncidentStatusDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load incidentStatus on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.incidentStatus).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

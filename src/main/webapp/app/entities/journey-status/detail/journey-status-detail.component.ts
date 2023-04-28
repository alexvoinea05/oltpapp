import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJourneyStatus } from '../journey-status.model';

@Component({
  selector: 'jhi-journey-status-detail',
  templateUrl: './journey-status-detail.component.html',
})
export class JourneyStatusDetailComponent implements OnInit {
  journeyStatus: IJourneyStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatus }) => {
      this.journeyStatus = journeyStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

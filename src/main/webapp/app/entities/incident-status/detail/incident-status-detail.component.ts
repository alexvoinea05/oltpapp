import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidentStatus } from '../incident-status.model';

@Component({
  selector: 'jhi-incident-status-detail',
  templateUrl: './incident-status-detail.component.html',
})
export class IncidentStatusDetailComponent implements OnInit {
  incidentStatus: IIncidentStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incidentStatus }) => {
      this.incidentStatus = incidentStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

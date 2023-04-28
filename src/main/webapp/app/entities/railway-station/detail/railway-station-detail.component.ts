import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayStation } from '../railway-station.model';

@Component({
  selector: 'jhi-railway-station-detail',
  templateUrl: './railway-station-detail.component.html',
})
export class RailwayStationDetailComponent implements OnInit {
  railwayStation: IRailwayStation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStation }) => {
      this.railwayStation = railwayStation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

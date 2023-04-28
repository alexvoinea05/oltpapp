import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRailwayType } from '../railway-type.model';

@Component({
  selector: 'jhi-railway-type-detail',
  templateUrl: './railway-type-detail.component.html',
})
export class RailwayTypeDetailComponent implements OnInit {
  railwayType: IRailwayType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayType }) => {
      this.railwayType = railwayType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFuelType } from '../fuel-type.model';

@Component({
  selector: 'jhi-fuel-type-detail',
  templateUrl: './fuel-type-detail.component.html',
})
export class FuelTypeDetailComponent implements OnInit {
  fuelType: IFuelType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelType }) => {
      this.fuelType = fuelType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

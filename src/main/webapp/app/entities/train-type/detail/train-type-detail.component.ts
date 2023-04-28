import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrainType } from '../train-type.model';

@Component({
  selector: 'jhi-train-type-detail',
  templateUrl: './train-type-detail.component.html',
})
export class TrainTypeDetailComponent implements OnInit {
  trainType: ITrainType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainType }) => {
      this.trainType = trainType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}

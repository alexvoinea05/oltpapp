import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { TrainTypeFormService, TrainTypeFormGroup } from './train-type-form.service';
import { ITrainType } from '../train-type.model';
import { TrainTypeService } from '../service/train-type.service';

@Component({
  selector: 'jhi-train-type-update',
  templateUrl: './train-type-update.component.html',
})
export class TrainTypeUpdateComponent implements OnInit {
  isSaving = false;
  trainType: ITrainType | null = null;

  editForm: TrainTypeFormGroup = this.trainTypeFormService.createTrainTypeFormGroup();

  constructor(
    protected trainTypeService: TrainTypeService,
    protected trainTypeFormService: TrainTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ trainType }) => {
      this.trainType = trainType;
      if (trainType) {
        this.updateForm(trainType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const trainType = this.trainTypeFormService.getTrainType(this.editForm);
    if (trainType.id !== null) {
      this.subscribeToSaveResponse(this.trainTypeService.update(trainType));
    } else {
      this.subscribeToSaveResponse(this.trainTypeService.create(trainType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrainType>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(trainType: ITrainType): void {
    this.trainType = trainType;
    this.trainTypeFormService.resetForm(this.editForm, trainType);
  }
}

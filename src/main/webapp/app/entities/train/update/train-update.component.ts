import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { TrainFormService, TrainFormGroup } from './train-form.service';
import { ITrain } from '../train.model';
import { TrainService } from '../service/train.service';
import { IFuelType } from 'app/entities/fuel-type/fuel-type.model';
import { FuelTypeService } from 'app/entities/fuel-type/service/fuel-type.service';
import { ITrainType } from 'app/entities/train-type/train-type.model';
import { TrainTypeService } from 'app/entities/train-type/service/train-type.service';

@Component({
  selector: 'jhi-train-update',
  templateUrl: './train-update.component.html',
})
export class TrainUpdateComponent implements OnInit {
  isSaving = false;
  train: ITrain | null = null;

  fuelTypesSharedCollection: IFuelType[] = [];
  trainTypesSharedCollection: ITrainType[] = [];

  editForm: TrainFormGroup = this.trainFormService.createTrainFormGroup();

  constructor(
    protected trainService: TrainService,
    protected trainFormService: TrainFormService,
    protected fuelTypeService: FuelTypeService,
    protected trainTypeService: TrainTypeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareFuelType = (o1: IFuelType | null, o2: IFuelType | null): boolean => this.fuelTypeService.compareFuelType(o1, o2);

  compareTrainType = (o1: ITrainType | null, o2: ITrainType | null): boolean => this.trainTypeService.compareTrainType(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ train }) => {
      this.train = train;
      if (train) {
        this.updateForm(train);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const train = this.trainFormService.getTrain(this.editForm);
    if (train.id !== null) {
      this.subscribeToSaveResponse(this.trainService.update(train));
    } else {
      this.subscribeToSaveResponse(this.trainService.create(train));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrain>>): void {
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

  protected updateForm(train: ITrain): void {
    this.train = train;
    this.trainFormService.resetForm(this.editForm, train);

    this.fuelTypesSharedCollection = this.fuelTypeService.addFuelTypeToCollectionIfMissing<IFuelType>(
      this.fuelTypesSharedCollection,
      train.idFuelType
    );
    this.trainTypesSharedCollection = this.trainTypeService.addTrainTypeToCollectionIfMissing<ITrainType>(
      this.trainTypesSharedCollection,
      train.idTrainType
    );
  }

  protected loadRelationshipsOptions(): void {
    this.fuelTypeService
      .query()
      .pipe(map((res: HttpResponse<IFuelType[]>) => res.body ?? []))
      .pipe(
        map((fuelTypes: IFuelType[]) => this.fuelTypeService.addFuelTypeToCollectionIfMissing<IFuelType>(fuelTypes, this.train?.idFuelType))
      )
      .subscribe((fuelTypes: IFuelType[]) => (this.fuelTypesSharedCollection = fuelTypes));

    this.trainTypeService
      .query()
      .pipe(map((res: HttpResponse<ITrainType[]>) => res.body ?? []))
      .pipe(
        map((trainTypes: ITrainType[]) =>
          this.trainTypeService.addTrainTypeToCollectionIfMissing<ITrainType>(trainTypes, this.train?.idTrainType)
        )
      )
      .subscribe((trainTypes: ITrainType[]) => (this.trainTypesSharedCollection = trainTypes));
  }
}

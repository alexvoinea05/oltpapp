import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { FuelTypeFormService, FuelTypeFormGroup } from './fuel-type-form.service';
import { IFuelType } from '../fuel-type.model';
import { FuelTypeService } from '../service/fuel-type.service';

@Component({
  selector: 'jhi-fuel-type-update',
  templateUrl: './fuel-type-update.component.html',
})
export class FuelTypeUpdateComponent implements OnInit {
  isSaving = false;
  fuelType: IFuelType | null = null;

  editForm: FuelTypeFormGroup = this.fuelTypeFormService.createFuelTypeFormGroup();

  constructor(
    protected fuelTypeService: FuelTypeService,
    protected fuelTypeFormService: FuelTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fuelType }) => {
      this.fuelType = fuelType;
      if (fuelType) {
        this.updateForm(fuelType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fuelType = this.fuelTypeFormService.getFuelType(this.editForm);
    if (fuelType.id !== null) {
      this.subscribeToSaveResponse(this.fuelTypeService.update(fuelType));
    } else {
      this.subscribeToSaveResponse(this.fuelTypeService.create(fuelType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuelType>>): void {
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

  protected updateForm(fuelType: IFuelType): void {
    this.fuelType = fuelType;
    this.fuelTypeFormService.resetForm(this.editForm, fuelType);
  }
}

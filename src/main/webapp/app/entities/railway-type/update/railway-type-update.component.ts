import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { RailwayTypeFormService, RailwayTypeFormGroup } from './railway-type-form.service';
import { IRailwayType } from '../railway-type.model';
import { RailwayTypeService } from '../service/railway-type.service';

@Component({
  selector: 'jhi-railway-type-update',
  templateUrl: './railway-type-update.component.html',
})
export class RailwayTypeUpdateComponent implements OnInit {
  isSaving = false;
  railwayType: IRailwayType | null = null;

  editForm: RailwayTypeFormGroup = this.railwayTypeFormService.createRailwayTypeFormGroup();

  constructor(
    protected railwayTypeService: RailwayTypeService,
    protected railwayTypeFormService: RailwayTypeFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayType }) => {
      this.railwayType = railwayType;
      if (railwayType) {
        this.updateForm(railwayType);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayType = this.railwayTypeFormService.getRailwayType(this.editForm);
    if (railwayType.id !== null) {
      this.subscribeToSaveResponse(this.railwayTypeService.update(railwayType));
    } else {
      this.subscribeToSaveResponse(this.railwayTypeService.create(railwayType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayType>>): void {
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

  protected updateForm(railwayType: IRailwayType): void {
    this.railwayType = railwayType;
    this.railwayTypeFormService.resetForm(this.editForm, railwayType);
  }
}

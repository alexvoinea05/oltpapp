import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IncidentStatusFormService, IncidentStatusFormGroup } from './incident-status-form.service';
import { IIncidentStatus } from '../incident-status.model';
import { IncidentStatusService } from '../service/incident-status.service';

@Component({
  selector: 'jhi-incident-status-update',
  templateUrl: './incident-status-update.component.html',
})
export class IncidentStatusUpdateComponent implements OnInit {
  isSaving = false;
  incidentStatus: IIncidentStatus | null = null;

  editForm: IncidentStatusFormGroup = this.incidentStatusFormService.createIncidentStatusFormGroup();

  constructor(
    protected incidentStatusService: IncidentStatusService,
    protected incidentStatusFormService: IncidentStatusFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incidentStatus }) => {
      this.incidentStatus = incidentStatus;
      if (incidentStatus) {
        this.updateForm(incidentStatus);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incidentStatus = this.incidentStatusFormService.getIncidentStatus(this.editForm);
    if (incidentStatus.id !== null) {
      this.subscribeToSaveResponse(this.incidentStatusService.update(incidentStatus));
    } else {
      this.subscribeToSaveResponse(this.incidentStatusService.create(incidentStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidentStatus>>): void {
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

  protected updateForm(incidentStatus: IIncidentStatus): void {
    this.incidentStatus = incidentStatus;
    this.incidentStatusFormService.resetForm(this.editForm, incidentStatus);
  }
}

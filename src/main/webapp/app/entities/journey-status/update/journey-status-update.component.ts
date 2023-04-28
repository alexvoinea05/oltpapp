import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { JourneyStatusFormService, JourneyStatusFormGroup } from './journey-status-form.service';
import { IJourneyStatus } from '../journey-status.model';
import { JourneyStatusService } from '../service/journey-status.service';

@Component({
  selector: 'jhi-journey-status-update',
  templateUrl: './journey-status-update.component.html',
})
export class JourneyStatusUpdateComponent implements OnInit {
  isSaving = false;
  journeyStatus: IJourneyStatus | null = null;

  editForm: JourneyStatusFormGroup = this.journeyStatusFormService.createJourneyStatusFormGroup();

  constructor(
    protected journeyStatusService: JourneyStatusService,
    protected journeyStatusFormService: JourneyStatusFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journeyStatus }) => {
      this.journeyStatus = journeyStatus;
      if (journeyStatus) {
        this.updateForm(journeyStatus);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journeyStatus = this.journeyStatusFormService.getJourneyStatus(this.editForm);
    if (journeyStatus.id !== null) {
      this.subscribeToSaveResponse(this.journeyStatusService.update(journeyStatus));
    } else {
      this.subscribeToSaveResponse(this.journeyStatusService.create(journeyStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourneyStatus>>): void {
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

  protected updateForm(journeyStatus: IJourneyStatus): void {
    this.journeyStatus = journeyStatus;
    this.journeyStatusFormService.resetForm(this.editForm, journeyStatus);
  }
}

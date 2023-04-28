import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IncidentFormService, IncidentFormGroup } from './incident-form.service';
import { IIncident } from '../incident.model';
import { IncidentService } from '../service/incident.service';
import { IIncidentStatus } from 'app/entities/incident-status/incident-status.model';
import { IncidentStatusService } from 'app/entities/incident-status/service/incident-status.service';
import { IAppUser } from 'app/entities/app-user/app-user.model';
import { AppUserService } from 'app/entities/app-user/service/app-user.service';
import { IJourney } from 'app/entities/journey/journey.model';
import { JourneyService } from 'app/entities/journey/service/journey.service';

@Component({
  selector: 'jhi-incident-update',
  templateUrl: './incident-update.component.html',
})
export class IncidentUpdateComponent implements OnInit {
  isSaving = false;
  incident: IIncident | null = null;

  incidentStatusesSharedCollection: IIncidentStatus[] = [];
  appUsersSharedCollection: IAppUser[] = [];
  journeysSharedCollection: IJourney[] = [];

  editForm: IncidentFormGroup = this.incidentFormService.createIncidentFormGroup();

  constructor(
    protected incidentService: IncidentService,
    protected incidentFormService: IncidentFormService,
    protected incidentStatusService: IncidentStatusService,
    protected appUserService: AppUserService,
    protected journeyService: JourneyService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareIncidentStatus = (o1: IIncidentStatus | null, o2: IIncidentStatus | null): boolean =>
    this.incidentStatusService.compareIncidentStatus(o1, o2);

  compareAppUser = (o1: IAppUser | null, o2: IAppUser | null): boolean => this.appUserService.compareAppUser(o1, o2);

  compareJourney = (o1: IJourney | null, o2: IJourney | null): boolean => this.journeyService.compareJourney(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incident }) => {
      this.incident = incident;
      if (incident) {
        this.updateForm(incident);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incident = this.incidentFormService.getIncident(this.editForm);
    if (incident.id !== null) {
      this.subscribeToSaveResponse(this.incidentService.update(incident));
    } else {
      this.subscribeToSaveResponse(this.incidentService.create(incident));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>): void {
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

  protected updateForm(incident: IIncident): void {
    this.incident = incident;
    this.incidentFormService.resetForm(this.editForm, incident);

    this.incidentStatusesSharedCollection = this.incidentStatusService.addIncidentStatusToCollectionIfMissing<IIncidentStatus>(
      this.incidentStatusesSharedCollection,
      incident.idIncidentStatus
    );
    this.appUsersSharedCollection = this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(
      this.appUsersSharedCollection,
      incident.idAppUser
    );
    this.journeysSharedCollection = this.journeyService.addJourneyToCollectionIfMissing<IJourney>(
      this.journeysSharedCollection,
      incident.idJourney
    );
  }

  protected loadRelationshipsOptions(): void {
    this.incidentStatusService
      .query()
      .pipe(map((res: HttpResponse<IIncidentStatus[]>) => res.body ?? []))
      .pipe(
        map((incidentStatuses: IIncidentStatus[]) =>
          this.incidentStatusService.addIncidentStatusToCollectionIfMissing<IIncidentStatus>(
            incidentStatuses,
            this.incident?.idIncidentStatus
          )
        )
      )
      .subscribe((incidentStatuses: IIncidentStatus[]) => (this.incidentStatusesSharedCollection = incidentStatuses));

    this.appUserService
      .query()
      .pipe(map((res: HttpResponse<IAppUser[]>) => res.body ?? []))
      .pipe(
        map((appUsers: IAppUser[]) => this.appUserService.addAppUserToCollectionIfMissing<IAppUser>(appUsers, this.incident?.idAppUser))
      )
      .subscribe((appUsers: IAppUser[]) => (this.appUsersSharedCollection = appUsers));

    this.journeyService
      .query()
      .pipe(map((res: HttpResponse<IJourney[]>) => res.body ?? []))
      .pipe(
        map((journeys: IJourney[]) => this.journeyService.addJourneyToCollectionIfMissing<IJourney>(journeys, this.incident?.idJourney))
      )
      .subscribe((journeys: IJourney[]) => (this.journeysSharedCollection = journeys));
  }
}

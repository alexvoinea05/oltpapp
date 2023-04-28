import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { JourneyFormService, JourneyFormGroup } from './journey-form.service';
import { IJourney } from '../journey.model';
import { JourneyService } from '../service/journey.service';
import { IJourneyStatus } from 'app/entities/journey-status/journey-status.model';
import { JourneyStatusService } from 'app/entities/journey-status/service/journey-status.service';
import { ITrain } from 'app/entities/train/train.model';
import { TrainService } from 'app/entities/train/service/train.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { IRailwayStation } from 'app/entities/railway-station/railway-station.model';
import { RailwayStationService } from 'app/entities/railway-station/service/railway-station.service';

@Component({
  selector: 'jhi-journey-update',
  templateUrl: './journey-update.component.html',
})
export class JourneyUpdateComponent implements OnInit {
  isSaving = false;
  journey: IJourney | null = null;

  journeyStatusesSharedCollection: IJourneyStatus[] = [];
  trainsSharedCollection: ITrain[] = [];
  companiesSharedCollection: ICompany[] = [];
  railwayStationsSharedCollection: IRailwayStation[] = [];

  editForm: JourneyFormGroup = this.journeyFormService.createJourneyFormGroup();

  constructor(
    protected journeyService: JourneyService,
    protected journeyFormService: JourneyFormService,
    protected journeyStatusService: JourneyStatusService,
    protected trainService: TrainService,
    protected companyService: CompanyService,
    protected railwayStationService: RailwayStationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareJourneyStatus = (o1: IJourneyStatus | null, o2: IJourneyStatus | null): boolean =>
    this.journeyStatusService.compareJourneyStatus(o1, o2);

  compareTrain = (o1: ITrain | null, o2: ITrain | null): boolean => this.trainService.compareTrain(o1, o2);

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareRailwayStation = (o1: IRailwayStation | null, o2: IRailwayStation | null): boolean =>
    this.railwayStationService.compareRailwayStation(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ journey }) => {
      this.journey = journey;
      if (journey) {
        this.updateForm(journey);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const journey = this.journeyFormService.getJourney(this.editForm);
    if (journey.id !== null) {
      this.subscribeToSaveResponse(this.journeyService.update(journey));
    } else {
      this.subscribeToSaveResponse(this.journeyService.create(journey));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJourney>>): void {
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

  protected updateForm(journey: IJourney): void {
    this.journey = journey;
    this.journeyFormService.resetForm(this.editForm, journey);

    this.journeyStatusesSharedCollection = this.journeyStatusService.addJourneyStatusToCollectionIfMissing<IJourneyStatus>(
      this.journeyStatusesSharedCollection,
      journey.idJourneyStatus
    );
    this.trainsSharedCollection = this.trainService.addTrainToCollectionIfMissing<ITrain>(this.trainsSharedCollection, journey.idTrain);
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing<ICompany>(
      this.companiesSharedCollection,
      journey.idCompany
    );
    this.railwayStationsSharedCollection = this.railwayStationService.addRailwayStationToCollectionIfMissing<IRailwayStation>(
      this.railwayStationsSharedCollection,
      journey.idRailwayStationDeparture,
      journey.idRailwayStationArrival
    );
  }

  protected loadRelationshipsOptions(): void {
    this.journeyStatusService
      .query()
      .pipe(map((res: HttpResponse<IJourneyStatus[]>) => res.body ?? []))
      .pipe(
        map((journeyStatuses: IJourneyStatus[]) =>
          this.journeyStatusService.addJourneyStatusToCollectionIfMissing<IJourneyStatus>(journeyStatuses, this.journey?.idJourneyStatus)
        )
      )
      .subscribe((journeyStatuses: IJourneyStatus[]) => (this.journeyStatusesSharedCollection = journeyStatuses));

    this.trainService
      .query()
      .pipe(map((res: HttpResponse<ITrain[]>) => res.body ?? []))
      .pipe(map((trains: ITrain[]) => this.trainService.addTrainToCollectionIfMissing<ITrain>(trains, this.journey?.idTrain)))
      .subscribe((trains: ITrain[]) => (this.trainsSharedCollection = trains));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.journey?.idCompany))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));

    this.railwayStationService
      .query()
      .pipe(map((res: HttpResponse<IRailwayStation[]>) => res.body ?? []))
      .pipe(
        map((railwayStations: IRailwayStation[]) =>
          this.railwayStationService.addRailwayStationToCollectionIfMissing<IRailwayStation>(
            railwayStations,
            this.journey?.idRailwayStationDeparture,
            this.journey?.idRailwayStationArrival
          )
        )
      )
      .subscribe((railwayStations: IRailwayStation[]) => (this.railwayStationsSharedCollection = railwayStations));
  }
}

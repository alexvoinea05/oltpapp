import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { RailwayStationFormService, RailwayStationFormGroup } from './railway-station-form.service';
import { IRailwayStation } from '../railway-station.model';
import { RailwayStationService } from '../service/railway-station.service';
import { IRailwayType } from 'app/entities/railway-type/railway-type.model';
import { RailwayTypeService } from 'app/entities/railway-type/service/railway-type.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';

@Component({
  selector: 'jhi-railway-station-update',
  templateUrl: './railway-station-update.component.html',
})
export class RailwayStationUpdateComponent implements OnInit {
  isSaving = false;
  railwayStation: IRailwayStation | null = null;

  railwayTypesSharedCollection: IRailwayType[] = [];
  addressesSharedCollection: IAddress[] = [];

  editForm: RailwayStationFormGroup = this.railwayStationFormService.createRailwayStationFormGroup();

  constructor(
    protected railwayStationService: RailwayStationService,
    protected railwayStationFormService: RailwayStationFormService,
    protected railwayTypeService: RailwayTypeService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRailwayType = (o1: IRailwayType | null, o2: IRailwayType | null): boolean => this.railwayTypeService.compareRailwayType(o1, o2);

  compareAddress = (o1: IAddress | null, o2: IAddress | null): boolean => this.addressService.compareAddress(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ railwayStation }) => {
      this.railwayStation = railwayStation;
      if (railwayStation) {
        this.updateForm(railwayStation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const railwayStation = this.railwayStationFormService.getRailwayStation(this.editForm);
    if (railwayStation.id !== null) {
      this.subscribeToSaveResponse(this.railwayStationService.update(railwayStation));
    } else {
      this.subscribeToSaveResponse(this.railwayStationService.create(railwayStation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRailwayStation>>): void {
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

  protected updateForm(railwayStation: IRailwayStation): void {
    this.railwayStation = railwayStation;
    this.railwayStationFormService.resetForm(this.editForm, railwayStation);

    this.railwayTypesSharedCollection = this.railwayTypeService.addRailwayTypeToCollectionIfMissing<IRailwayType>(
      this.railwayTypesSharedCollection,
      railwayStation.idRailwayType
    );
    this.addressesSharedCollection = this.addressService.addAddressToCollectionIfMissing<IAddress>(
      this.addressesSharedCollection,
      railwayStation.idAddress
    );
  }

  protected loadRelationshipsOptions(): void {
    this.railwayTypeService
      .query()
      .pipe(map((res: HttpResponse<IRailwayType[]>) => res.body ?? []))
      .pipe(
        map((railwayTypes: IRailwayType[]) =>
          this.railwayTypeService.addRailwayTypeToCollectionIfMissing<IRailwayType>(railwayTypes, this.railwayStation?.idRailwayType)
        )
      )
      .subscribe((railwayTypes: IRailwayType[]) => (this.railwayTypesSharedCollection = railwayTypes));

    this.addressService
      .query()
      .pipe(map((res: HttpResponse<IAddress[]>) => res.body ?? []))
      .pipe(
        map((addresses: IAddress[]) =>
          this.addressService.addAddressToCollectionIfMissing<IAddress>(addresses, this.railwayStation?.idAddress)
        )
      )
      .subscribe((addresses: IAddress[]) => (this.addressesSharedCollection = addresses));
  }
}

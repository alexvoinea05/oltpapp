import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RailwayStationFormService } from './railway-station-form.service';
import { RailwayStationService } from '../service/railway-station.service';
import { IRailwayStation } from '../railway-station.model';
import { IRailwayType } from 'app/entities/railway-type/railway-type.model';
import { RailwayTypeService } from 'app/entities/railway-type/service/railway-type.service';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';

import { RailwayStationUpdateComponent } from './railway-station-update.component';

describe('RailwayStation Management Update Component', () => {
  let comp: RailwayStationUpdateComponent;
  let fixture: ComponentFixture<RailwayStationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let railwayStationFormService: RailwayStationFormService;
  let railwayStationService: RailwayStationService;
  let railwayTypeService: RailwayTypeService;
  let addressService: AddressService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [RailwayStationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RailwayStationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RailwayStationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    railwayStationFormService = TestBed.inject(RailwayStationFormService);
    railwayStationService = TestBed.inject(RailwayStationService);
    railwayTypeService = TestBed.inject(RailwayTypeService);
    addressService = TestBed.inject(AddressService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call RailwayType query and add missing value', () => {
      const railwayStation: IRailwayStation = { id: 456 };
      const idRailwayType: IRailwayType = { id: 53968 };
      railwayStation.idRailwayType = idRailwayType;

      const railwayTypeCollection: IRailwayType[] = [{ id: 62570 }];
      jest.spyOn(railwayTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: railwayTypeCollection })));
      const additionalRailwayTypes = [idRailwayType];
      const expectedCollection: IRailwayType[] = [...additionalRailwayTypes, ...railwayTypeCollection];
      jest.spyOn(railwayTypeService, 'addRailwayTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ railwayStation });
      comp.ngOnInit();

      expect(railwayTypeService.query).toHaveBeenCalled();
      expect(railwayTypeService.addRailwayTypeToCollectionIfMissing).toHaveBeenCalledWith(
        railwayTypeCollection,
        ...additionalRailwayTypes.map(expect.objectContaining)
      );
      expect(comp.railwayTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Address query and add missing value', () => {
      const railwayStation: IRailwayStation = { id: 456 };
      const idAddress: IAddress = { id: 19068 };
      railwayStation.idAddress = idAddress;

      const addressCollection: IAddress[] = [{ id: 56649 }];
      jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
      const additionalAddresses = [idAddress];
      const expectedCollection: IAddress[] = [...additionalAddresses, ...addressCollection];
      jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ railwayStation });
      comp.ngOnInit();

      expect(addressService.query).toHaveBeenCalled();
      expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(
        addressCollection,
        ...additionalAddresses.map(expect.objectContaining)
      );
      expect(comp.addressesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const railwayStation: IRailwayStation = { id: 456 };
      const idRailwayType: IRailwayType = { id: 78849 };
      railwayStation.idRailwayType = idRailwayType;
      const idAddress: IAddress = { id: 18396 };
      railwayStation.idAddress = idAddress;

      activatedRoute.data = of({ railwayStation });
      comp.ngOnInit();

      expect(comp.railwayTypesSharedCollection).toContain(idRailwayType);
      expect(comp.addressesSharedCollection).toContain(idAddress);
      expect(comp.railwayStation).toEqual(railwayStation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStation>>();
      const railwayStation = { id: 123 };
      jest.spyOn(railwayStationFormService, 'getRailwayStation').mockReturnValue(railwayStation);
      jest.spyOn(railwayStationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStation }));
      saveSubject.complete();

      // THEN
      expect(railwayStationFormService.getRailwayStation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(railwayStationService.update).toHaveBeenCalledWith(expect.objectContaining(railwayStation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStation>>();
      const railwayStation = { id: 123 };
      jest.spyOn(railwayStationFormService, 'getRailwayStation').mockReturnValue({ id: null });
      jest.spyOn(railwayStationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: railwayStation }));
      saveSubject.complete();

      // THEN
      expect(railwayStationFormService.getRailwayStation).toHaveBeenCalled();
      expect(railwayStationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRailwayStation>>();
      const railwayStation = { id: 123 };
      jest.spyOn(railwayStationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ railwayStation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(railwayStationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRailwayType', () => {
      it('Should forward to railwayTypeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(railwayTypeService, 'compareRailwayType');
        comp.compareRailwayType(entity, entity2);
        expect(railwayTypeService.compareRailwayType).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAddress', () => {
      it('Should forward to addressService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(addressService, 'compareAddress');
        comp.compareAddress(entity, entity2);
        expect(addressService.compareAddress).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

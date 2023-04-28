import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRailwayStation } from '../railway-station.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../railway-station.test-samples';

import { RailwayStationService } from './railway-station.service';

const requireRestSample: IRailwayStation = {
  ...sampleWithRequiredData,
};

describe('RailwayStation Service', () => {
  let service: RailwayStationService;
  let httpMock: HttpTestingController;
  let expectedResult: IRailwayStation | IRailwayStation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RailwayStationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a RailwayStation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const railwayStation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(railwayStation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a RailwayStation', () => {
      const railwayStation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(railwayStation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a RailwayStation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of RailwayStation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a RailwayStation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRailwayStationToCollectionIfMissing', () => {
      it('should add a RailwayStation to an empty array', () => {
        const railwayStation: IRailwayStation = sampleWithRequiredData;
        expectedResult = service.addRailwayStationToCollectionIfMissing([], railwayStation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStation);
      });

      it('should not add a RailwayStation to an array that contains it', () => {
        const railwayStation: IRailwayStation = sampleWithRequiredData;
        const railwayStationCollection: IRailwayStation[] = [
          {
            ...railwayStation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRailwayStationToCollectionIfMissing(railwayStationCollection, railwayStation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a RailwayStation to an array that doesn't contain it", () => {
        const railwayStation: IRailwayStation = sampleWithRequiredData;
        const railwayStationCollection: IRailwayStation[] = [sampleWithPartialData];
        expectedResult = service.addRailwayStationToCollectionIfMissing(railwayStationCollection, railwayStation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStation);
      });

      it('should add only unique RailwayStation to an array', () => {
        const railwayStationArray: IRailwayStation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const railwayStationCollection: IRailwayStation[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationToCollectionIfMissing(railwayStationCollection, ...railwayStationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const railwayStation: IRailwayStation = sampleWithRequiredData;
        const railwayStation2: IRailwayStation = sampleWithPartialData;
        expectedResult = service.addRailwayStationToCollectionIfMissing([], railwayStation, railwayStation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(railwayStation);
        expect(expectedResult).toContain(railwayStation2);
      });

      it('should accept null and undefined values', () => {
        const railwayStation: IRailwayStation = sampleWithRequiredData;
        expectedResult = service.addRailwayStationToCollectionIfMissing([], null, railwayStation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(railwayStation);
      });

      it('should return initial array if no RailwayStation is added', () => {
        const railwayStationCollection: IRailwayStation[] = [sampleWithRequiredData];
        expectedResult = service.addRailwayStationToCollectionIfMissing(railwayStationCollection, undefined, null);
        expect(expectedResult).toEqual(railwayStationCollection);
      });
    });

    describe('compareRailwayStation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRailwayStation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRailwayStation(entity1, entity2);
        const compareResult2 = service.compareRailwayStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRailwayStation(entity1, entity2);
        const compareResult2 = service.compareRailwayStation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRailwayStation(entity1, entity2);
        const compareResult2 = service.compareRailwayStation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

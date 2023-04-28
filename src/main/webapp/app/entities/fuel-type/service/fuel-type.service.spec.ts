import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFuelType } from '../fuel-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fuel-type.test-samples';

import { FuelTypeService } from './fuel-type.service';

const requireRestSample: IFuelType = {
  ...sampleWithRequiredData,
};

describe('FuelType Service', () => {
  let service: FuelTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelType | IFuelType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTypeService);
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

    it('should create a FuelType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const fuelType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelType', () => {
      const fuelType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTypeToCollectionIfMissing', () => {
      it('should add a FuelType to an empty array', () => {
        const fuelType: IFuelType = sampleWithRequiredData;
        expectedResult = service.addFuelTypeToCollectionIfMissing([], fuelType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelType);
      });

      it('should not add a FuelType to an array that contains it', () => {
        const fuelType: IFuelType = sampleWithRequiredData;
        const fuelTypeCollection: IFuelType[] = [
          {
            ...fuelType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTypeToCollectionIfMissing(fuelTypeCollection, fuelType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelType to an array that doesn't contain it", () => {
        const fuelType: IFuelType = sampleWithRequiredData;
        const fuelTypeCollection: IFuelType[] = [sampleWithPartialData];
        expectedResult = service.addFuelTypeToCollectionIfMissing(fuelTypeCollection, fuelType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelType);
      });

      it('should add only unique FuelType to an array', () => {
        const fuelTypeArray: IFuelType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTypeCollection: IFuelType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeToCollectionIfMissing(fuelTypeCollection, ...fuelTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelType: IFuelType = sampleWithRequiredData;
        const fuelType2: IFuelType = sampleWithPartialData;
        expectedResult = service.addFuelTypeToCollectionIfMissing([], fuelType, fuelType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelType);
        expect(expectedResult).toContain(fuelType2);
      });

      it('should accept null and undefined values', () => {
        const fuelType: IFuelType = sampleWithRequiredData;
        expectedResult = service.addFuelTypeToCollectionIfMissing([], null, fuelType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelType);
      });

      it('should return initial array if no FuelType is added', () => {
        const fuelTypeCollection: IFuelType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTypeToCollectionIfMissing(fuelTypeCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTypeCollection);
      });
    });

    describe('compareFuelType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuelType(entity1, entity2);
        const compareResult2 = service.compareFuelType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuelType(entity1, entity2);
        const compareResult2 = service.compareFuelType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuelType(entity1, entity2);
        const compareResult2 = service.compareFuelType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

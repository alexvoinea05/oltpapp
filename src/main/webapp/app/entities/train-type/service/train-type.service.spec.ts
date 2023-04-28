import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITrainType } from '../train-type.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../train-type.test-samples';

import { TrainTypeService } from './train-type.service';

const requireRestSample: ITrainType = {
  ...sampleWithRequiredData,
};

describe('TrainType Service', () => {
  let service: TrainTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: ITrainType | ITrainType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TrainTypeService);
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

    it('should create a TrainType', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const trainType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(trainType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TrainType', () => {
      const trainType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(trainType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TrainType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TrainType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TrainType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTrainTypeToCollectionIfMissing', () => {
      it('should add a TrainType to an empty array', () => {
        const trainType: ITrainType = sampleWithRequiredData;
        expectedResult = service.addTrainTypeToCollectionIfMissing([], trainType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainType);
      });

      it('should not add a TrainType to an array that contains it', () => {
        const trainType: ITrainType = sampleWithRequiredData;
        const trainTypeCollection: ITrainType[] = [
          {
            ...trainType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTrainTypeToCollectionIfMissing(trainTypeCollection, trainType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TrainType to an array that doesn't contain it", () => {
        const trainType: ITrainType = sampleWithRequiredData;
        const trainTypeCollection: ITrainType[] = [sampleWithPartialData];
        expectedResult = service.addTrainTypeToCollectionIfMissing(trainTypeCollection, trainType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainType);
      });

      it('should add only unique TrainType to an array', () => {
        const trainTypeArray: ITrainType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const trainTypeCollection: ITrainType[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeToCollectionIfMissing(trainTypeCollection, ...trainTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const trainType: ITrainType = sampleWithRequiredData;
        const trainType2: ITrainType = sampleWithPartialData;
        expectedResult = service.addTrainTypeToCollectionIfMissing([], trainType, trainType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(trainType);
        expect(expectedResult).toContain(trainType2);
      });

      it('should accept null and undefined values', () => {
        const trainType: ITrainType = sampleWithRequiredData;
        expectedResult = service.addTrainTypeToCollectionIfMissing([], null, trainType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(trainType);
      });

      it('should return initial array if no TrainType is added', () => {
        const trainTypeCollection: ITrainType[] = [sampleWithRequiredData];
        expectedResult = service.addTrainTypeToCollectionIfMissing(trainTypeCollection, undefined, null);
        expect(expectedResult).toEqual(trainTypeCollection);
      });
    });

    describe('compareTrainType', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTrainType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTrainType(entity1, entity2);
        const compareResult2 = service.compareTrainType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTrainType(entity1, entity2);
        const compareResult2 = service.compareTrainType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTrainType(entity1, entity2);
        const compareResult2 = service.compareTrainType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

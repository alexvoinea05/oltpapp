import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJourneyStatus } from '../journey-status.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../journey-status.test-samples';

import { JourneyStatusService } from './journey-status.service';

const requireRestSample: IJourneyStatus = {
  ...sampleWithRequiredData,
};

describe('JourneyStatus Service', () => {
  let service: JourneyStatusService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourneyStatus | IJourneyStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JourneyStatusService);
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

    it('should create a JourneyStatus', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const journeyStatus = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(journeyStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a JourneyStatus', () => {
      const journeyStatus = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(journeyStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a JourneyStatus', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of JourneyStatus', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a JourneyStatus', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourneyStatusToCollectionIfMissing', () => {
      it('should add a JourneyStatus to an empty array', () => {
        const journeyStatus: IJourneyStatus = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusToCollectionIfMissing([], journeyStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatus);
      });

      it('should not add a JourneyStatus to an array that contains it', () => {
        const journeyStatus: IJourneyStatus = sampleWithRequiredData;
        const journeyStatusCollection: IJourneyStatus[] = [
          {
            ...journeyStatus,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourneyStatusToCollectionIfMissing(journeyStatusCollection, journeyStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a JourneyStatus to an array that doesn't contain it", () => {
        const journeyStatus: IJourneyStatus = sampleWithRequiredData;
        const journeyStatusCollection: IJourneyStatus[] = [sampleWithPartialData];
        expectedResult = service.addJourneyStatusToCollectionIfMissing(journeyStatusCollection, journeyStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatus);
      });

      it('should add only unique JourneyStatus to an array', () => {
        const journeyStatusArray: IJourneyStatus[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const journeyStatusCollection: IJourneyStatus[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusToCollectionIfMissing(journeyStatusCollection, ...journeyStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const journeyStatus: IJourneyStatus = sampleWithRequiredData;
        const journeyStatus2: IJourneyStatus = sampleWithPartialData;
        expectedResult = service.addJourneyStatusToCollectionIfMissing([], journeyStatus, journeyStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journeyStatus);
        expect(expectedResult).toContain(journeyStatus2);
      });

      it('should accept null and undefined values', () => {
        const journeyStatus: IJourneyStatus = sampleWithRequiredData;
        expectedResult = service.addJourneyStatusToCollectionIfMissing([], null, journeyStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journeyStatus);
      });

      it('should return initial array if no JourneyStatus is added', () => {
        const journeyStatusCollection: IJourneyStatus[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyStatusToCollectionIfMissing(journeyStatusCollection, undefined, null);
        expect(expectedResult).toEqual(journeyStatusCollection);
      });
    });

    describe('compareJourneyStatus', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourneyStatus(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJourneyStatus(entity1, entity2);
        const compareResult2 = service.compareJourneyStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJourneyStatus(entity1, entity2);
        const compareResult2 = service.compareJourneyStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJourneyStatus(entity1, entity2);
        const compareResult2 = service.compareJourneyStatus(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

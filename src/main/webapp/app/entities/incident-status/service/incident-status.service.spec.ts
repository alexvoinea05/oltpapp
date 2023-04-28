import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IIncidentStatus } from '../incident-status.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../incident-status.test-samples';

import { IncidentStatusService } from './incident-status.service';

const requireRestSample: IIncidentStatus = {
  ...sampleWithRequiredData,
};

describe('IncidentStatus Service', () => {
  let service: IncidentStatusService;
  let httpMock: HttpTestingController;
  let expectedResult: IIncidentStatus | IIncidentStatus[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IncidentStatusService);
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

    it('should create a IncidentStatus', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const incidentStatus = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(incidentStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a IncidentStatus', () => {
      const incidentStatus = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(incidentStatus).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a IncidentStatus', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of IncidentStatus', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a IncidentStatus', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIncidentStatusToCollectionIfMissing', () => {
      it('should add a IncidentStatus to an empty array', () => {
        const incidentStatus: IIncidentStatus = sampleWithRequiredData;
        expectedResult = service.addIncidentStatusToCollectionIfMissing([], incidentStatus);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(incidentStatus);
      });

      it('should not add a IncidentStatus to an array that contains it', () => {
        const incidentStatus: IIncidentStatus = sampleWithRequiredData;
        const incidentStatusCollection: IIncidentStatus[] = [
          {
            ...incidentStatus,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIncidentStatusToCollectionIfMissing(incidentStatusCollection, incidentStatus);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a IncidentStatus to an array that doesn't contain it", () => {
        const incidentStatus: IIncidentStatus = sampleWithRequiredData;
        const incidentStatusCollection: IIncidentStatus[] = [sampleWithPartialData];
        expectedResult = service.addIncidentStatusToCollectionIfMissing(incidentStatusCollection, incidentStatus);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(incidentStatus);
      });

      it('should add only unique IncidentStatus to an array', () => {
        const incidentStatusArray: IIncidentStatus[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const incidentStatusCollection: IIncidentStatus[] = [sampleWithRequiredData];
        expectedResult = service.addIncidentStatusToCollectionIfMissing(incidentStatusCollection, ...incidentStatusArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const incidentStatus: IIncidentStatus = sampleWithRequiredData;
        const incidentStatus2: IIncidentStatus = sampleWithPartialData;
        expectedResult = service.addIncidentStatusToCollectionIfMissing([], incidentStatus, incidentStatus2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(incidentStatus);
        expect(expectedResult).toContain(incidentStatus2);
      });

      it('should accept null and undefined values', () => {
        const incidentStatus: IIncidentStatus = sampleWithRequiredData;
        expectedResult = service.addIncidentStatusToCollectionIfMissing([], null, incidentStatus, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(incidentStatus);
      });

      it('should return initial array if no IncidentStatus is added', () => {
        const incidentStatusCollection: IIncidentStatus[] = [sampleWithRequiredData];
        expectedResult = service.addIncidentStatusToCollectionIfMissing(incidentStatusCollection, undefined, null);
        expect(expectedResult).toEqual(incidentStatusCollection);
      });
    });

    describe('compareIncidentStatus', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareIncidentStatus(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareIncidentStatus(entity1, entity2);
        const compareResult2 = service.compareIncidentStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareIncidentStatus(entity1, entity2);
        const compareResult2 = service.compareIncidentStatus(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareIncidentStatus(entity1, entity2);
        const compareResult2 = service.compareIncidentStatus(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

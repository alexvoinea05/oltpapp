import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IJourney } from '../journey.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../journey.test-samples';

import { JourneyService, RestJourney } from './journey.service';

const requireRestSample: RestJourney = {
  ...sampleWithRequiredData,
  actualDepartureTime: sampleWithRequiredData.actualDepartureTime?.toJSON(),
  plannedDepartureTime: sampleWithRequiredData.plannedDepartureTime?.toJSON(),
  actualArrivalTime: sampleWithRequiredData.actualArrivalTime?.toJSON(),
  plannedArrivalTime: sampleWithRequiredData.plannedArrivalTime?.toJSON(),
};

describe('Journey Service', () => {
  let service: JourneyService;
  let httpMock: HttpTestingController;
  let expectedResult: IJourney | IJourney[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JourneyService);
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

    it('should create a Journey', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const journey = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(journey).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Journey', () => {
      const journey = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(journey).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Journey', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Journey', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Journey', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJourneyToCollectionIfMissing', () => {
      it('should add a Journey to an empty array', () => {
        const journey: IJourney = sampleWithRequiredData;
        expectedResult = service.addJourneyToCollectionIfMissing([], journey);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journey);
      });

      it('should not add a Journey to an array that contains it', () => {
        const journey: IJourney = sampleWithRequiredData;
        const journeyCollection: IJourney[] = [
          {
            ...journey,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJourneyToCollectionIfMissing(journeyCollection, journey);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Journey to an array that doesn't contain it", () => {
        const journey: IJourney = sampleWithRequiredData;
        const journeyCollection: IJourney[] = [sampleWithPartialData];
        expectedResult = service.addJourneyToCollectionIfMissing(journeyCollection, journey);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journey);
      });

      it('should add only unique Journey to an array', () => {
        const journeyArray: IJourney[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const journeyCollection: IJourney[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyToCollectionIfMissing(journeyCollection, ...journeyArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const journey: IJourney = sampleWithRequiredData;
        const journey2: IJourney = sampleWithPartialData;
        expectedResult = service.addJourneyToCollectionIfMissing([], journey, journey2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(journey);
        expect(expectedResult).toContain(journey2);
      });

      it('should accept null and undefined values', () => {
        const journey: IJourney = sampleWithRequiredData;
        expectedResult = service.addJourneyToCollectionIfMissing([], null, journey, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(journey);
      });

      it('should return initial array if no Journey is added', () => {
        const journeyCollection: IJourney[] = [sampleWithRequiredData];
        expectedResult = service.addJourneyToCollectionIfMissing(journeyCollection, undefined, null);
        expect(expectedResult).toEqual(journeyCollection);
      });
    });

    describe('compareJourney', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJourney(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJourney(entity1, entity2);
        const compareResult2 = service.compareJourney(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJourney(entity1, entity2);
        const compareResult2 = service.compareJourney(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJourney(entity1, entity2);
        const compareResult2 = service.compareJourney(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

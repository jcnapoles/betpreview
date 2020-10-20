import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CompetitionService } from 'app/entities/competition/competition.service';
import { ICompetition, Competition } from 'app/shared/model/competition.model';
import { TypeCompetition } from 'app/shared/model/enumerations/type-competition.model';

describe('Service Tests', () => {
  describe('Competition Service', () => {
    let injector: TestBed;
    let service: CompetitionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompetition;
    let expectedResult: ICompetition | ICompetition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CompetitionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Competition(0, 'AAAAAAA', 'image/png', 'AAAAAAA', false, false, TypeCompetition.DOMESTIC, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Competition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Competition', () => {
        const returnedFromService = Object.assign(
          {
            competitionName: 'BBBBBB',
            competitionLogo: 'BBBBBB',
            isCup: true,
            active: true,
            type: 'BBBBBB',
            sportscribeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Competition', () => {
        const returnedFromService = Object.assign(
          {
            competitionName: 'BBBBBB',
            competitionLogo: 'BBBBBB',
            isCup: true,
            active: true,
            type: 'BBBBBB',
            sportscribeId: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Competition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

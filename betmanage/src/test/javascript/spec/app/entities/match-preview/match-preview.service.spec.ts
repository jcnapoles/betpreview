import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';
import { IMatchPreview, MatchPreview } from 'app/shared/model/match-preview.model';

describe('Service Tests', () => {
  describe('MatchPreview Service', () => {
    let injector: TestBed;
    let service: MatchPreviewService;
    let httpMock: HttpTestingController;
    let elemDefault: IMatchPreview;
    let expectedResult: IMatchPreview | IMatchPreview[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MatchPreviewService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new MatchPreview(0, 0, 'AAAAAAA', 0, 0, 'AAAAAAA', 'AAAAAAA', 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MatchPreview()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            fixtureId: 1,
            blurbFull: 'BBBBBB',
            hometeamId: 1,
            visitorteamId: 1,
            hometeamName: 'BBBBBB',
            visitorteamName: 'BBBBBB',
            leagueId: 1,
            league: 'BBBBBB',
            formationImg: 'BBBBBB',
            fixtureImg: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            fixtureId: 1,
            blurbFull: 'BBBBBB',
            hometeamId: 1,
            visitorteamId: 1,
            hometeamName: 'BBBBBB',
            visitorteamName: 'BBBBBB',
            leagueId: 1,
            league: 'BBBBBB',
            formationImg: 'BBBBBB',
            fixtureImg: 'BBBBBB',
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

      it('should delete a MatchPreview', () => {
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

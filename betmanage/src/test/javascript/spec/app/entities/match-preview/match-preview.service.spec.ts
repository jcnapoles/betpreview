import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';
import { IMatchPreview, MatchPreview } from 'app/shared/model/match-preview.model';
import { LanguageEnum } from 'app/shared/model/enumerations/language-enum.model';

describe('Service Tests', () => {
  describe('MatchPreview Service', () => {
    let injector: TestBed;
    let service: MatchPreviewService;
    let httpMock: HttpTestingController;
    let elemDefault: IMatchPreview;
    let expectedResult: IMatchPreview | IMatchPreview[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MatchPreviewService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MatchPreview(
        0,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        LanguageEnum.EN
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startUtcTimestamp: currentDate.format(DATE_TIME_FORMAT),
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startUtcTimestamp: currentDate.format(DATE_TIME_FORMAT),
            date: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startUtcTimestamp: currentDate,
            date: currentDate,
          },
          returnedFromService
        );

        service.create(new MatchPreview()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            blurbFull: 'BBBBBB',
            fixtureId: 1,
            hometeamId: 1,
            visitorteamId: 1,
            hometeamName: 'BBBBBB',
            visitorteamName: 'BBBBBB',
            leagueId: 1,
            league: 'BBBBBB',
            fixtureImg: 'BBBBBB',
            formationImg: 'BBBBBB',
            startUtcTimestamp: currentDate.format(DATE_TIME_FORMAT),
            venueName: 'BBBBBB',
            matchImg: 'BBBBBB',
            matchImaTxt: 'BBBBBB',
            headline: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            language: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startUtcTimestamp: currentDate,
            date: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MatchPreview', () => {
        const returnedFromService = Object.assign(
          {
            blurbFull: 'BBBBBB',
            fixtureId: 1,
            hometeamId: 1,
            visitorteamId: 1,
            hometeamName: 'BBBBBB',
            visitorteamName: 'BBBBBB',
            leagueId: 1,
            league: 'BBBBBB',
            fixtureImg: 'BBBBBB',
            formationImg: 'BBBBBB',
            startUtcTimestamp: currentDate.format(DATE_TIME_FORMAT),
            venueName: 'BBBBBB',
            matchImg: 'BBBBBB',
            matchImaTxt: 'BBBBBB',
            headline: 'BBBBBB',
            date: currentDate.format(DATE_FORMAT),
            language: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startUtcTimestamp: currentDate,
            date: currentDate,
          },
          returnedFromService
        );

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

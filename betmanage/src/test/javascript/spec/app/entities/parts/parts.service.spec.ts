import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PartsService } from 'app/entities/parts/parts.service';
import { IParts, Parts } from 'app/shared/model/parts.model';

describe('Service Tests', () => {
  describe('Parts Service', () => {
    let injector: TestBed;
    let service: PartsService;
    let httpMock: HttpTestingController;
    let elemDefault: IParts;
    let expectedResult: IParts | IParts[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PartsService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Parts(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Parts', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Parts()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Parts', () => {
        const returnedFromService = Object.assign(
          {
            intro: 'BBBBBB',
            weather: 'BBBBBB',
            homeLastResult: 'BBBBBB',
            visitorLastResult: 'BBBBBB',
            homeScorers: 'BBBBBB',
            visitorScorers: 'BBBBBB',
            lastMeetingResult: 'BBBBBB',
            lastMeetingScoring: 'BBBBBB',
            homeSidelined: 'BBBBBB',
            visitorSidelined: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Parts', () => {
        const returnedFromService = Object.assign(
          {
            intro: 'BBBBBB',
            weather: 'BBBBBB',
            homeLastResult: 'BBBBBB',
            visitorLastResult: 'BBBBBB',
            homeScorers: 'BBBBBB',
            visitorScorers: 'BBBBBB',
            lastMeetingResult: 'BBBBBB',
            lastMeetingScoring: 'BBBBBB',
            homeSidelined: 'BBBBBB',
            visitorSidelined: 'BBBBBB',
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

      it('should delete a Parts', () => {
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

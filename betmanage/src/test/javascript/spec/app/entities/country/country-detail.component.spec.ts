import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { CountryDetailComponent } from 'app/entities/country/country-detail.component';
import { Country } from 'app/shared/model/country.model';

describe('Component Tests', () => {
  describe('Country Management Detail Component', () => {
    let comp: CountryDetailComponent;
    let fixture: ComponentFixture<CountryDetailComponent>;
    const route = ({ data: of({ country: new Country(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [CountryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CountryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CountryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load country on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.country).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { SportDetailComponent } from 'app/entities/sport/sport-detail.component';
import { Sport } from 'app/shared/model/sport.model';

describe('Component Tests', () => {
  describe('Sport Management Detail Component', () => {
    let comp: SportDetailComponent;
    let fixture: ComponentFixture<SportDetailComponent>;
    const route = ({ data: of({ sport: new Sport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [SportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

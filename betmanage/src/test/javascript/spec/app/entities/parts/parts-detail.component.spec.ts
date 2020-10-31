import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { PartsDetailComponent } from 'app/entities/parts/parts-detail.component';
import { Parts } from 'app/shared/model/parts.model';

describe('Component Tests', () => {
  describe('Parts Management Detail Component', () => {
    let comp: PartsDetailComponent;
    let fixture: ComponentFixture<PartsDetailComponent>;
    const route = ({ data: of({ parts: new Parts(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [PartsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load parts on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.parts).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { ParagraphsDetailComponent } from 'app/entities/paragraphs/paragraphs-detail.component';
import { Paragraphs } from 'app/shared/model/paragraphs.model';

describe('Component Tests', () => {
  describe('Paragraphs Management Detail Component', () => {
    let comp: ParagraphsDetailComponent;
    let fixture: ComponentFixture<ParagraphsDetailComponent>;
    const route = ({ data: of({ paragraphs: new Paragraphs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [ParagraphsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ParagraphsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ParagraphsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paragraphs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paragraphs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

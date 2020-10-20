import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { MatchPreviewDetailComponent } from 'app/entities/match-preview/match-preview-detail.component';
import { MatchPreview } from 'app/shared/model/match-preview.model';

describe('Component Tests', () => {
  describe('MatchPreview Management Detail Component', () => {
    let comp: MatchPreviewDetailComponent;
    let fixture: ComponentFixture<MatchPreviewDetailComponent>;
    const route = ({ data: of({ matchPreview: new MatchPreview(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [MatchPreviewDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MatchPreviewDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MatchPreviewDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load matchPreview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matchPreview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

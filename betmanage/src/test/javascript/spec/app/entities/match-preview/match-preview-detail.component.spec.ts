import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BetmanageTestModule } from '../../../test.module';
import { MatchPreviewDetailComponent } from 'app/entities/match-preview/match-preview-detail.component';
import { MatchPreview } from 'app/shared/model/match-preview.model';

describe('Component Tests', () => {
  describe('MatchPreview Management Detail Component', () => {
    let comp: MatchPreviewDetailComponent;
    let fixture: ComponentFixture<MatchPreviewDetailComponent>;
    let dataUtils: JhiDataUtils;
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
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load matchPreview on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.matchPreview).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});

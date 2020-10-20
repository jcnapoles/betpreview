import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { MatchPreviewUpdateComponent } from 'app/entities/match-preview/match-preview-update.component';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';
import { MatchPreview } from 'app/shared/model/match-preview.model';

describe('Component Tests', () => {
  describe('MatchPreview Management Update Component', () => {
    let comp: MatchPreviewUpdateComponent;
    let fixture: ComponentFixture<MatchPreviewUpdateComponent>;
    let service: MatchPreviewService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [MatchPreviewUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MatchPreviewUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MatchPreviewUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MatchPreviewService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatchPreview(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MatchPreview();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

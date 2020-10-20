import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { ParagraphsUpdateComponent } from 'app/entities/paragraphs/paragraphs-update.component';
import { ParagraphsService } from 'app/entities/paragraphs/paragraphs.service';
import { Paragraphs } from 'app/shared/model/paragraphs.model';

describe('Component Tests', () => {
  describe('Paragraphs Management Update Component', () => {
    let comp: ParagraphsUpdateComponent;
    let fixture: ComponentFixture<ParagraphsUpdateComponent>;
    let service: ParagraphsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [ParagraphsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ParagraphsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParagraphsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParagraphsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Paragraphs(123);
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
        const entity = new Paragraphs();
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

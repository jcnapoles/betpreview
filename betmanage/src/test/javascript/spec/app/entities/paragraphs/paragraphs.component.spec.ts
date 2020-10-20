import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetmanageTestModule } from '../../../test.module';
import { ParagraphsComponent } from 'app/entities/paragraphs/paragraphs.component';
import { ParagraphsService } from 'app/entities/paragraphs/paragraphs.service';
import { Paragraphs } from 'app/shared/model/paragraphs.model';

describe('Component Tests', () => {
  describe('Paragraphs Management Component', () => {
    let comp: ParagraphsComponent;
    let fixture: ComponentFixture<ParagraphsComponent>;
    let service: ParagraphsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [ParagraphsComponent],
      })
        .overrideTemplate(ParagraphsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ParagraphsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ParagraphsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Paragraphs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paragraphs && comp.paragraphs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

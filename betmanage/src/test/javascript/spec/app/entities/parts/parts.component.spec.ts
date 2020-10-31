import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetmanageTestModule } from '../../../test.module';
import { PartsComponent } from 'app/entities/parts/parts.component';
import { PartsService } from 'app/entities/parts/parts.service';
import { Parts } from 'app/shared/model/parts.model';

describe('Component Tests', () => {
  describe('Parts Management Component', () => {
    let comp: PartsComponent;
    let fixture: ComponentFixture<PartsComponent>;
    let service: PartsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [PartsComponent],
      })
        .overrideTemplate(PartsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PartsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PartsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Parts(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.parts && comp.parts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

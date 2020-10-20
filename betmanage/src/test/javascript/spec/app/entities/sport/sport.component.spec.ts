import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetmanageTestModule } from '../../../test.module';
import { SportComponent } from 'app/entities/sport/sport.component';
import { SportService } from 'app/entities/sport/sport.service';
import { Sport } from 'app/shared/model/sport.model';

describe('Component Tests', () => {
  describe('Sport Management Component', () => {
    let comp: SportComponent;
    let fixture: ComponentFixture<SportComponent>;
    let service: SportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [SportComponent],
      })
        .overrideTemplate(SportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Sport(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sports && comp.sports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

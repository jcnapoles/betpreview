import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BetmanageTestModule } from '../../../test.module';
import { TeamSocialComponent } from 'app/entities/team-social/team-social.component';
import { TeamSocialService } from 'app/entities/team-social/team-social.service';
import { TeamSocial } from 'app/shared/model/team-social.model';

describe('Component Tests', () => {
  describe('TeamSocial Management Component', () => {
    let comp: TeamSocialComponent;
    let fixture: ComponentFixture<TeamSocialComponent>;
    let service: TeamSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [TeamSocialComponent],
      })
        .overrideTemplate(TeamSocialComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamSocialComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamSocialService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TeamSocial(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.teamSocials && comp.teamSocials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { TeamSocialDetailComponent } from 'app/entities/team-social/team-social-detail.component';
import { TeamSocial } from 'app/shared/model/team-social.model';

describe('Component Tests', () => {
  describe('TeamSocial Management Detail Component', () => {
    let comp: TeamSocialDetailComponent;
    let fixture: ComponentFixture<TeamSocialDetailComponent>;
    const route = ({ data: of({ teamSocial: new TeamSocial(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [TeamSocialDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TeamSocialDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TeamSocialDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load teamSocial on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.teamSocial).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BetmanageTestModule } from '../../../test.module';
import { TeamSocialUpdateComponent } from 'app/entities/team-social/team-social-update.component';
import { TeamSocialService } from 'app/entities/team-social/team-social.service';
import { TeamSocial } from 'app/shared/model/team-social.model';

describe('Component Tests', () => {
  describe('TeamSocial Management Update Component', () => {
    let comp: TeamSocialUpdateComponent;
    let fixture: ComponentFixture<TeamSocialUpdateComponent>;
    let service: TeamSocialService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BetmanageTestModule],
        declarations: [TeamSocialUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TeamSocialUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TeamSocialUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TeamSocialService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TeamSocial(123);
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
        const entity = new TeamSocial();
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

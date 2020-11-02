import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITeamSocial, TeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from './team-social.service';

@Component({
  selector: 'jhi-team-social-update',
  templateUrl: './team-social-update.component.html',
})
export class TeamSocialUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    homeTeamId: [],
    visitorTeamId: [],
    match: [],
  });

  constructor(protected teamSocialService: TeamSocialService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSocial }) => {
      this.updateForm(teamSocial);
    });
  }

  updateForm(teamSocial: ITeamSocial): void {
    this.editForm.patchValue({
      id: teamSocial.id,
      homeTeamId: teamSocial.homeTeamId,
      visitorTeamId: teamSocial.visitorTeamId,
      match: teamSocial.match,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const teamSocial = this.createFromForm();
    if (teamSocial.id !== undefined) {
      this.subscribeToSaveResponse(this.teamSocialService.update(teamSocial));
    } else {
      this.subscribeToSaveResponse(this.teamSocialService.create(teamSocial));
    }
  }

  private createFromForm(): ITeamSocial {
    return {
      ...new TeamSocial(),
      id: this.editForm.get(['id'])!.value,
      homeTeamId: this.editForm.get(['homeTeamId'])!.value,
      visitorTeamId: this.editForm.get(['visitorTeamId'])!.value,
      match: this.editForm.get(['match'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeamSocial>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}

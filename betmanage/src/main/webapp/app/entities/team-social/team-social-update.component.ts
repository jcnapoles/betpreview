import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ITeamSocial, TeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from './team-social.service';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';

@Component({
  selector: 'jhi-team-social-update',
  templateUrl: './team-social-update.component.html',
})
export class TeamSocialUpdateComponent implements OnInit {
  isSaving = false;
  matchpreviews: IMatchPreview[] = [];

  editForm = this.fb.group({
    id: [],
    homeTeamId: [],
    visitorTeamId: [],
    match: [],
    matchPreview: [],
  });

  constructor(
    protected teamSocialService: TeamSocialService,
    protected matchPreviewService: MatchPreviewService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSocial }) => {
      this.updateForm(teamSocial);

      this.matchPreviewService
        .query({ filter: 'teamsocial-is-null' })
        .pipe(
          map((res: HttpResponse<IMatchPreview[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMatchPreview[]) => {
          if (!teamSocial.matchPreview || !teamSocial.matchPreview.id) {
            this.matchpreviews = resBody;
          } else {
            this.matchPreviewService
              .find(teamSocial.matchPreview.id)
              .pipe(
                map((subRes: HttpResponse<IMatchPreview>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMatchPreview[]) => (this.matchpreviews = concatRes));
          }
        });
    });
  }

  updateForm(teamSocial: ITeamSocial): void {
    this.editForm.patchValue({
      id: teamSocial.id,
      homeTeamId: teamSocial.homeTeamId,
      visitorTeamId: teamSocial.visitorTeamId,
      match: teamSocial.match,
      matchPreview: teamSocial.matchPreview,
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
      matchPreview: this.editForm.get(['matchPreview'])!.value,
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

  trackById(index: number, item: IMatchPreview): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISocialMedia, SocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team/team.service';

@Component({
  selector: 'jhi-social-media-update',
  templateUrl: './social-media-update.component.html',
})
export class SocialMediaUpdateComponent implements OnInit {
  isSaving = false;
  teams: ITeam[] = [];

  editForm = this.fb.group({
    id: [],
    tag: [],
    platform: [],
    team: [],
  });

  constructor(
    protected socialMediaService: SocialMediaService,
    protected teamService: TeamService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socialMedia }) => {
      this.updateForm(socialMedia);

      this.teamService.query().subscribe((res: HttpResponse<ITeam[]>) => (this.teams = res.body || []));
    });
  }

  updateForm(socialMedia: ISocialMedia): void {
    this.editForm.patchValue({
      id: socialMedia.id,
      tag: socialMedia.tag,
      platform: socialMedia.platform,
      team: socialMedia.team,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const socialMedia = this.createFromForm();
    if (socialMedia.id !== undefined) {
      this.subscribeToSaveResponse(this.socialMediaService.update(socialMedia));
    } else {
      this.subscribeToSaveResponse(this.socialMediaService.create(socialMedia));
    }
  }

  private createFromForm(): ISocialMedia {
    return {
      ...new SocialMedia(),
      id: this.editForm.get(['id'])!.value,
      tag: this.editForm.get(['tag'])!.value,
      platform: this.editForm.get(['platform'])!.value,
      team: this.editForm.get(['team'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocialMedia>>): void {
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

  trackById(index: number, item: ITeam): any {
    return item.id;
  }
}

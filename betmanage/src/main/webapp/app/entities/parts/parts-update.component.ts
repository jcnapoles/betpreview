import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParts, Parts } from 'app/shared/model/parts.model';
import { PartsService } from './parts.service';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';

@Component({
  selector: 'jhi-parts-update',
  templateUrl: './parts-update.component.html',
})
export class PartsUpdateComponent implements OnInit {
  isSaving = false;
  matchpreviews: IMatchPreview[] = [];

  editForm = this.fb.group({
    id: [],
    intro: [],
    weather: [],
    homeLastResult: [],
    visitorLastResult: [],
    homeScorers: [],
    visitorScorers: [],
    lastMeetingResult: [],
    lastMeetingScoring: [],
    homeSidelined: [],
    visitorSidelined: [],
    parts: [],
  });

  constructor(
    protected partsService: PartsService,
    protected matchPreviewService: MatchPreviewService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parts }) => {
      this.updateForm(parts);

      this.matchPreviewService.query().subscribe((res: HttpResponse<IMatchPreview[]>) => (this.matchpreviews = res.body || []));
    });
  }

  updateForm(parts: IParts): void {
    this.editForm.patchValue({
      id: parts.id,
      intro: parts.intro,
      weather: parts.weather,
      homeLastResult: parts.homeLastResult,
      visitorLastResult: parts.visitorLastResult,
      homeScorers: parts.homeScorers,
      visitorScorers: parts.visitorScorers,
      lastMeetingResult: parts.lastMeetingResult,
      lastMeetingScoring: parts.lastMeetingScoring,
      homeSidelined: parts.homeSidelined,
      visitorSidelined: parts.visitorSidelined,
      parts: parts.parts,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parts = this.createFromForm();
    if (parts.id !== undefined) {
      this.subscribeToSaveResponse(this.partsService.update(parts));
    } else {
      this.subscribeToSaveResponse(this.partsService.create(parts));
    }
  }

  private createFromForm(): IParts {
    return {
      ...new Parts(),
      id: this.editForm.get(['id'])!.value,
      intro: this.editForm.get(['intro'])!.value,
      weather: this.editForm.get(['weather'])!.value,
      homeLastResult: this.editForm.get(['homeLastResult'])!.value,
      visitorLastResult: this.editForm.get(['visitorLastResult'])!.value,
      homeScorers: this.editForm.get(['homeScorers'])!.value,
      visitorScorers: this.editForm.get(['visitorScorers'])!.value,
      lastMeetingResult: this.editForm.get(['lastMeetingResult'])!.value,
      lastMeetingScoring: this.editForm.get(['lastMeetingScoring'])!.value,
      homeSidelined: this.editForm.get(['homeSidelined'])!.value,
      visitorSidelined: this.editForm.get(['visitorSidelined'])!.value,
      parts: this.editForm.get(['parts'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParts>>): void {
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

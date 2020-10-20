import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IParagraphs, Paragraphs } from 'app/shared/model/paragraphs.model';
import { ParagraphsService } from './paragraphs.service';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';

@Component({
  selector: 'jhi-paragraphs-update',
  templateUrl: './paragraphs-update.component.html',
})
export class ParagraphsUpdateComponent implements OnInit {
  isSaving = false;
  matchpreviews: IMatchPreview[] = [];

  editForm = this.fb.group({
    id: [],
    content: [],
    blurbSplit: [],
  });

  constructor(
    protected paragraphsService: ParagraphsService,
    protected matchPreviewService: MatchPreviewService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paragraphs }) => {
      this.updateForm(paragraphs);

      this.matchPreviewService.query().subscribe((res: HttpResponse<IMatchPreview[]>) => (this.matchpreviews = res.body || []));
    });
  }

  updateForm(paragraphs: IParagraphs): void {
    this.editForm.patchValue({
      id: paragraphs.id,
      content: paragraphs.content,
      blurbSplit: paragraphs.blurbSplit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paragraphs = this.createFromForm();
    if (paragraphs.id !== undefined) {
      this.subscribeToSaveResponse(this.paragraphsService.update(paragraphs));
    } else {
      this.subscribeToSaveResponse(this.paragraphsService.create(paragraphs));
    }
  }

  private createFromForm(): IParagraphs {
    return {
      ...new Paragraphs(),
      id: this.editForm.get(['id'])!.value,
      content: this.editForm.get(['content'])!.value,
      blurbSplit: this.editForm.get(['blurbSplit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParagraphs>>): void {
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

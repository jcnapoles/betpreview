import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITitle, Title } from 'app/shared/model/title.model';
import { TitleService } from './title.service';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from 'app/entities/match-preview/match-preview.service';

@Component({
  selector: 'jhi-title-update',
  templateUrl: './title-update.component.html',
})
export class TitleUpdateComponent implements OnInit {
  isSaving = false;
  matchpreviews: IMatchPreview[] = [];

  editForm = this.fb.group({
    id: [],
    titleText: [],
    quickItems: [],
  });

  constructor(
    protected titleService: TitleService,
    protected matchPreviewService: MatchPreviewService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ title }) => {
      this.updateForm(title);

      this.matchPreviewService.query().subscribe((res: HttpResponse<IMatchPreview[]>) => (this.matchpreviews = res.body || []));
    });
  }

  updateForm(title: ITitle): void {
    this.editForm.patchValue({
      id: title.id,
      titleText: title.titleText,
      quickItems: title.quickItems,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const title = this.createFromForm();
    if (title.id !== undefined) {
      this.subscribeToSaveResponse(this.titleService.update(title));
    } else {
      this.subscribeToSaveResponse(this.titleService.create(title));
    }
  }

  private createFromForm(): ITitle {
    return {
      ...new Title(),
      id: this.editForm.get(['id'])!.value,
      titleText: this.editForm.get(['titleText'])!.value,
      quickItems: this.editForm.get(['quickItems'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITitle>>): void {
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

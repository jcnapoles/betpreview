import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMatchPreview } from 'app/shared/model/match-preview.model';

@Component({
  selector: 'jhi-match-preview-detail',
  templateUrl: './match-preview-detail.component.html',
})
export class MatchPreviewDetailComponent implements OnInit {
  matchPreview: IMatchPreview | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matchPreview }) => (this.matchPreview = matchPreview));
  }

  previousState(): void {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IParagraphs } from 'app/shared/model/paragraphs.model';

@Component({
  selector: 'jhi-paragraphs-detail',
  templateUrl: './paragraphs-detail.component.html',
})
export class ParagraphsDetailComponent implements OnInit {
  paragraphs: IParagraphs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paragraphs }) => (this.paragraphs = paragraphs));
  }

  previousState(): void {
    window.history.back();
  }
}

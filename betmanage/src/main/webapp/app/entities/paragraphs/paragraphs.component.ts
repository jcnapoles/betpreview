import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParagraphs } from 'app/shared/model/paragraphs.model';
import { ParagraphsService } from './paragraphs.service';
import { ParagraphsDeleteDialogComponent } from './paragraphs-delete-dialog.component';

@Component({
  selector: 'jhi-paragraphs',
  templateUrl: './paragraphs.component.html',
})
export class ParagraphsComponent implements OnInit, OnDestroy {
  paragraphs?: IParagraphs[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected paragraphsService: ParagraphsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.paragraphsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IParagraphs[]>) => (this.paragraphs = res.body || []));
      return;
    }

    this.paragraphsService.query().subscribe((res: HttpResponse<IParagraphs[]>) => (this.paragraphs = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParagraphs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParagraphs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParagraphs(): void {
    this.eventSubscriber = this.eventManager.subscribe('paragraphsListModification', () => this.loadAll());
  }

  delete(paragraphs: IParagraphs): void {
    const modalRef = this.modalService.open(ParagraphsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paragraphs = paragraphs;
  }
}

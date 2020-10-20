import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatchPreview } from 'app/shared/model/match-preview.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MatchPreviewService } from './match-preview.service';
import { MatchPreviewDeleteDialogComponent } from './match-preview-delete-dialog.component';

@Component({
  selector: 'jhi-match-preview',
  templateUrl: './match-preview.component.html',
})
export class MatchPreviewComponent implements OnInit, OnDestroy {
  matchPreviews: IMatchPreview[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;

  constructor(
    protected matchPreviewService: MatchPreviewService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.matchPreviews = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.matchPreviewService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe((res: HttpResponse<IMatchPreview[]>) => this.paginateMatchPreviews(res.body, res.headers));
      return;
    }

    this.matchPreviewService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IMatchPreview[]>) => this.paginateMatchPreviews(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.matchPreviews = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.matchPreviews = [];
    this.links = {
      last: 0,
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMatchPreviews();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMatchPreview): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMatchPreviews(): void {
    this.eventSubscriber = this.eventManager.subscribe('matchPreviewListModification', () => this.reset());
  }

  delete(matchPreview: IMatchPreview): void {
    const modalRef = this.modalService.open(MatchPreviewDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.matchPreview = matchPreview;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMatchPreviews(data: IMatchPreview[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.matchPreviews.push(data[i]);
      }
    }
  }
}

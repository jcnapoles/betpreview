import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IParts } from 'app/shared/model/parts.model';
import { PartsService } from './parts.service';
import { PartsDeleteDialogComponent } from './parts-delete-dialog.component';

@Component({
  selector: 'jhi-parts',
  templateUrl: './parts.component.html',
})
export class PartsComponent implements OnInit, OnDestroy {
  parts?: IParts[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected partsService: PartsService,
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
      this.partsService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IParts[]>) => (this.parts = res.body || []));
      return;
    }

    this.partsService.query().subscribe((res: HttpResponse<IParts[]>) => (this.parts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInParts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IParts): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInParts(): void {
    this.eventSubscriber = this.eventManager.subscribe('partsListModification', () => this.loadAll());
  }

  delete(parts: IParts): void {
    const modalRef = this.modalService.open(PartsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.parts = parts;
  }
}

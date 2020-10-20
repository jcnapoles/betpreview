import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';
import { SportDeleteDialogComponent } from './sport-delete-dialog.component';

@Component({
  selector: 'jhi-sport',
  templateUrl: './sport.component.html',
})
export class SportComponent implements OnInit, OnDestroy {
  sports?: ISport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected sportService: SportService,
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
      this.sportService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));
      return;
    }

    this.sportService.query().subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSports(): void {
    this.eventSubscriber = this.eventManager.subscribe('sportListModification', () => this.loadAll());
  }

  delete(sport: ISport): void {
    const modalRef = this.modalService.open(SportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sport = sport;
  }
}

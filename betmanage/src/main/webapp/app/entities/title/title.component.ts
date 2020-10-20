import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITitle } from 'app/shared/model/title.model';
import { TitleService } from './title.service';
import { TitleDeleteDialogComponent } from './title-delete-dialog.component';

@Component({
  selector: 'jhi-title',
  templateUrl: './title.component.html',
})
export class TitleComponent implements OnInit, OnDestroy {
  titles?: ITitle[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected titleService: TitleService,
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
      this.titleService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITitle[]>) => (this.titles = res.body || []));
      return;
    }

    this.titleService.query().subscribe((res: HttpResponse<ITitle[]>) => (this.titles = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTitles();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITitle): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTitles(): void {
    this.eventSubscriber = this.eventManager.subscribe('titleListModification', () => this.loadAll());
  }

  delete(title: ITitle): void {
    const modalRef = this.modalService.open(TitleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.title = title;
  }
}

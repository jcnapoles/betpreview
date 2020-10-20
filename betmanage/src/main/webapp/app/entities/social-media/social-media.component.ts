import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';
import { SocialMediaDeleteDialogComponent } from './social-media-delete-dialog.component';

@Component({
  selector: 'jhi-social-media',
  templateUrl: './social-media.component.html',
})
export class SocialMediaComponent implements OnInit, OnDestroy {
  socialMedias?: ISocialMedia[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected socialMediaService: SocialMediaService,
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
      this.socialMediaService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ISocialMedia[]>) => (this.socialMedias = res.body || []));
      return;
    }

    this.socialMediaService.query().subscribe((res: HttpResponse<ISocialMedia[]>) => (this.socialMedias = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSocialMedias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISocialMedia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSocialMedias(): void {
    this.eventSubscriber = this.eventManager.subscribe('socialMediaListModification', () => this.loadAll());
  }

  delete(socialMedia: ISocialMedia): void {
    const modalRef = this.modalService.open(SocialMediaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.socialMedia = socialMedia;
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from './team-social.service';
import { TeamSocialDeleteDialogComponent } from './team-social-delete-dialog.component';

@Component({
  selector: 'jhi-team-social',
  templateUrl: './team-social.component.html',
})
export class TeamSocialComponent implements OnInit, OnDestroy {
  teamSocials?: ITeamSocial[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected teamSocialService: TeamSocialService,
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
      this.teamSocialService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<ITeamSocial[]>) => (this.teamSocials = res.body || []));
      return;
    }

    this.teamSocialService.query().subscribe((res: HttpResponse<ITeamSocial[]>) => (this.teamSocials = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTeamSocials();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITeamSocial): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTeamSocials(): void {
    this.eventSubscriber = this.eventManager.subscribe('teamSocialListModification', () => this.loadAll());
  }

  delete(teamSocial: ITeamSocial): void {
    const modalRef = this.modalService.open(TeamSocialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.teamSocial = teamSocial;
  }
}

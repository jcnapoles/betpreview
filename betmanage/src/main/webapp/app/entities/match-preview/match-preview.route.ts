import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatchPreview, MatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from './match-preview.service';
import { MatchPreviewComponent } from './match-preview.component';
import { MatchPreviewDetailComponent } from './match-preview-detail.component';
import { MatchPreviewUpdateComponent } from './match-preview-update.component';

@Injectable({ providedIn: 'root' })
export class MatchPreviewResolve implements Resolve<IMatchPreview> {
  constructor(private service: MatchPreviewService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatchPreview> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matchPreview: HttpResponse<MatchPreview>) => {
          if (matchPreview.body) {
            return of(matchPreview.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MatchPreview());
  }
}

export const matchPreviewRoute: Routes = [
  {
    path: '',
    component: MatchPreviewComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.matchPreview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MatchPreviewDetailComponent,
    resolve: {
      matchPreview: MatchPreviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.matchPreview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MatchPreviewUpdateComponent,
    resolve: {
      matchPreview: MatchPreviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.matchPreview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MatchPreviewUpdateComponent,
    resolve: {
      matchPreview: MatchPreviewResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.matchPreview.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

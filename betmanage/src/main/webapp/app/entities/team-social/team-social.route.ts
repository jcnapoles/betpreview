import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITeamSocial, TeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from './team-social.service';
import { TeamSocialComponent } from './team-social.component';
import { TeamSocialDetailComponent } from './team-social-detail.component';
import { TeamSocialUpdateComponent } from './team-social-update.component';

@Injectable({ providedIn: 'root' })
export class TeamSocialResolve implements Resolve<ITeamSocial> {
  constructor(private service: TeamSocialService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITeamSocial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((teamSocial: HttpResponse<TeamSocial>) => {
          if (teamSocial.body) {
            return of(teamSocial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TeamSocial());
  }
}

export const teamSocialRoute: Routes = [
  {
    path: '',
    component: TeamSocialComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.teamSocial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TeamSocialDetailComponent,
    resolve: {
      teamSocial: TeamSocialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.teamSocial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TeamSocialUpdateComponent,
    resolve: {
      teamSocial: TeamSocialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.teamSocial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TeamSocialUpdateComponent,
    resolve: {
      teamSocial: TeamSocialResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.teamSocial.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

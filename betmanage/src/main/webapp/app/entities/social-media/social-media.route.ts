import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISocialMedia, SocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';
import { SocialMediaComponent } from './social-media.component';
import { SocialMediaDetailComponent } from './social-media-detail.component';
import { SocialMediaUpdateComponent } from './social-media-update.component';

@Injectable({ providedIn: 'root' })
export class SocialMediaResolve implements Resolve<ISocialMedia> {
  constructor(private service: SocialMediaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocialMedia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((socialMedia: HttpResponse<SocialMedia>) => {
          if (socialMedia.body) {
            return of(socialMedia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SocialMedia());
  }
}

export const socialMediaRoute: Routes = [
  {
    path: '',
    component: SocialMediaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.socialMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocialMediaDetailComponent,
    resolve: {
      socialMedia: SocialMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.socialMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocialMediaUpdateComponent,
    resolve: {
      socialMedia: SocialMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.socialMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocialMediaUpdateComponent,
    resolve: {
      socialMedia: SocialMediaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.socialMedia.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

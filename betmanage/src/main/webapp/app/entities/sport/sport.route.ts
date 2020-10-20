import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISport, Sport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';
import { SportComponent } from './sport.component';
import { SportDetailComponent } from './sport-detail.component';
import { SportUpdateComponent } from './sport-update.component';

@Injectable({ providedIn: 'root' })
export class SportResolve implements Resolve<ISport> {
  constructor(private service: SportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sport: HttpResponse<Sport>) => {
          if (sport.body) {
            return of(sport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sport());
  }
}

export const sportRoute: Routes = [
  {
    path: '',
    component: SportComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.sport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SportDetailComponent,
    resolve: {
      sport: SportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.sport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SportUpdateComponent,
    resolve: {
      sport: SportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.sport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SportUpdateComponent,
    resolve: {
      sport: SportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.sport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

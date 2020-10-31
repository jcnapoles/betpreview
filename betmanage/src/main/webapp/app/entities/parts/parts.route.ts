import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParts, Parts } from 'app/shared/model/parts.model';
import { PartsService } from './parts.service';
import { PartsComponent } from './parts.component';
import { PartsDetailComponent } from './parts-detail.component';
import { PartsUpdateComponent } from './parts-update.component';

@Injectable({ providedIn: 'root' })
export class PartsResolve implements Resolve<IParts> {
  constructor(private service: PartsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParts> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((parts: HttpResponse<Parts>) => {
          if (parts.body) {
            return of(parts.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Parts());
  }
}

export const partsRoute: Routes = [
  {
    path: '',
    component: PartsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.parts.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartsDetailComponent,
    resolve: {
      parts: PartsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.parts.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartsUpdateComponent,
    resolve: {
      parts: PartsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.parts.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartsUpdateComponent,
    resolve: {
      parts: PartsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.parts.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IParagraphs, Paragraphs } from 'app/shared/model/paragraphs.model';
import { ParagraphsService } from './paragraphs.service';
import { ParagraphsComponent } from './paragraphs.component';
import { ParagraphsDetailComponent } from './paragraphs-detail.component';
import { ParagraphsUpdateComponent } from './paragraphs-update.component';

@Injectable({ providedIn: 'root' })
export class ParagraphsResolve implements Resolve<IParagraphs> {
  constructor(private service: ParagraphsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IParagraphs> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paragraphs: HttpResponse<Paragraphs>) => {
          if (paragraphs.body) {
            return of(paragraphs.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paragraphs());
  }
}

export const paragraphsRoute: Routes = [
  {
    path: '',
    component: ParagraphsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.paragraphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ParagraphsDetailComponent,
    resolve: {
      paragraphs: ParagraphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.paragraphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ParagraphsUpdateComponent,
    resolve: {
      paragraphs: ParagraphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.paragraphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ParagraphsUpdateComponent,
    resolve: {
      paragraphs: ParagraphsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'betmanageApp.paragraphs.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];

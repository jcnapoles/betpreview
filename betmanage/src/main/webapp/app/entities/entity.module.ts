import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'competition',
        loadChildren: () => import('./competition/competition.module').then(m => m.BetmanageCompetitionModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.BetmanageCountryModule),
      },
      {
        path: 'match-preview',
        loadChildren: () => import('./match-preview/match-preview.module').then(m => m.BetmanageMatchPreviewModule),
      },
      {
        path: 'paragraphs',
        loadChildren: () => import('./paragraphs/paragraphs.module').then(m => m.BetmanageParagraphsModule),
      },
      {
        path: 'social-media',
        loadChildren: () => import('./social-media/social-media.module').then(m => m.BetmanageSocialMediaModule),
      },
      {
        path: 'sport',
        loadChildren: () => import('./sport/sport.module').then(m => m.BetmanageSportModule),
      },
      {
        path: 'team',
        loadChildren: () => import('./team/team.module').then(m => m.BetmanageTeamModule),
      },
      {
        path: 'title',
        loadChildren: () => import('./title/title.module').then(m => m.BetmanageTitleModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BetmanageEntityModule {}

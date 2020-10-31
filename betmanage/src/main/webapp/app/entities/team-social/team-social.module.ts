import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { TeamSocialComponent } from './team-social.component';
import { TeamSocialDetailComponent } from './team-social-detail.component';
import { TeamSocialUpdateComponent } from './team-social-update.component';
import { TeamSocialDeleteDialogComponent } from './team-social-delete-dialog.component';
import { teamSocialRoute } from './team-social.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(teamSocialRoute)],
  declarations: [TeamSocialComponent, TeamSocialDetailComponent, TeamSocialUpdateComponent, TeamSocialDeleteDialogComponent],
  entryComponents: [TeamSocialDeleteDialogComponent],
})
export class BetmanageTeamSocialModule {}

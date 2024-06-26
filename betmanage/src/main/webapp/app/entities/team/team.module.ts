import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { TeamComponent } from './team.component';
import { TeamDetailComponent } from './team-detail.component';
import { TeamUpdateComponent } from './team-update.component';
import { TeamDeleteDialogComponent } from './team-delete-dialog.component';
import { teamRoute } from './team.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(teamRoute)],
  declarations: [TeamComponent, TeamDetailComponent, TeamUpdateComponent, TeamDeleteDialogComponent],
  entryComponents: [TeamDeleteDialogComponent],
})
export class BetmanageTeamModule {}

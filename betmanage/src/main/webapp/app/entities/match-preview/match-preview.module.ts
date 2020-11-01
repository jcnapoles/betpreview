import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { MatchPreviewComponent } from './match-preview.component';
import { MatchPreviewDetailComponent } from './match-preview-detail.component';
import { MatchPreviewUpdateComponent } from './match-preview-update.component';
import { MatchPreviewLoadComponent } from './match-preview-load.component';
import { MatchPreviewDeleteDialogComponent } from './match-preview-delete-dialog.component';
import { matchPreviewRoute } from './match-preview.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(matchPreviewRoute)],
  declarations: [MatchPreviewComponent, MatchPreviewDetailComponent, MatchPreviewUpdateComponent, MatchPreviewLoadComponent, MatchPreviewDeleteDialogComponent],
  entryComponents: [MatchPreviewDeleteDialogComponent],
})
export class BetmanageMatchPreviewModule {}

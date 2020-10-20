import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { SocialMediaComponent } from './social-media.component';
import { SocialMediaDetailComponent } from './social-media-detail.component';
import { SocialMediaUpdateComponent } from './social-media-update.component';
import { SocialMediaDeleteDialogComponent } from './social-media-delete-dialog.component';
import { socialMediaRoute } from './social-media.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(socialMediaRoute)],
  declarations: [SocialMediaComponent, SocialMediaDetailComponent, SocialMediaUpdateComponent, SocialMediaDeleteDialogComponent],
  entryComponents: [SocialMediaDeleteDialogComponent],
})
export class BetmanageSocialMediaModule {}

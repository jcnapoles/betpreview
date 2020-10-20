import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { ParagraphsComponent } from './paragraphs.component';
import { ParagraphsDetailComponent } from './paragraphs-detail.component';
import { ParagraphsUpdateComponent } from './paragraphs-update.component';
import { ParagraphsDeleteDialogComponent } from './paragraphs-delete-dialog.component';
import { paragraphsRoute } from './paragraphs.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(paragraphsRoute)],
  declarations: [ParagraphsComponent, ParagraphsDetailComponent, ParagraphsUpdateComponent, ParagraphsDeleteDialogComponent],
  entryComponents: [ParagraphsDeleteDialogComponent],
})
export class BetmanageParagraphsModule {}

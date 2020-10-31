import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BetmanageSharedModule } from 'app/shared/shared.module';
import { PartsComponent } from './parts.component';
import { PartsDetailComponent } from './parts-detail.component';
import { PartsUpdateComponent } from './parts-update.component';
import { PartsDeleteDialogComponent } from './parts-delete-dialog.component';
import { partsRoute } from './parts.route';

@NgModule({
  imports: [BetmanageSharedModule, RouterModule.forChild(partsRoute)],
  declarations: [PartsComponent, PartsDetailComponent, PartsUpdateComponent, PartsDeleteDialogComponent],
  entryComponents: [PartsDeleteDialogComponent],
})
export class BetmanagePartsModule {}

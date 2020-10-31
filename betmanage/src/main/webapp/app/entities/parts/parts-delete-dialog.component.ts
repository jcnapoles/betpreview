import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParts } from 'app/shared/model/parts.model';
import { PartsService } from './parts.service';

@Component({
  templateUrl: './parts-delete-dialog.component.html',
})
export class PartsDeleteDialogComponent {
  parts?: IParts;

  constructor(protected partsService: PartsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partsListModification');
      this.activeModal.close();
    });
  }
}

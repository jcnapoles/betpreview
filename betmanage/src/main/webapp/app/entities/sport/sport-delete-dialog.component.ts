import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
  templateUrl: './sport-delete-dialog.component.html',
})
export class SportDeleteDialogComponent {
  sport?: ISport;

  constructor(protected sportService: SportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sportListModification');
      this.activeModal.close();
    });
  }
}

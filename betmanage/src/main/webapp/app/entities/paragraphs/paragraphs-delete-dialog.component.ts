import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParagraphs } from 'app/shared/model/paragraphs.model';
import { ParagraphsService } from './paragraphs.service';

@Component({
  templateUrl: './paragraphs-delete-dialog.component.html',
})
export class ParagraphsDeleteDialogComponent {
  paragraphs?: IParagraphs;

  constructor(
    protected paragraphsService: ParagraphsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paragraphsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paragraphsListModification');
      this.activeModal.close();
    });
  }
}

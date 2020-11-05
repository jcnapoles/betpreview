import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { MatchPreviewService } from './match-preview.service';

@Component({
  templateUrl: './match-preview-send-dialog.component.html',
})
export class MatchPreviewSendDialogComponent {
  matchPreview?: IMatchPreview;

  constructor(
    protected matchPreviewService: MatchPreviewService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmSend(id: number): void {
    this.matchPreviewService.sendToBetPreview(id).subscribe(() => {
      this.eventManager.broadcast('matchPreviewListModification');
      this.activeModal.close();
    });
  }
}

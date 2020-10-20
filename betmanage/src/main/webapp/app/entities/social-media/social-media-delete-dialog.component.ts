import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISocialMedia } from 'app/shared/model/social-media.model';
import { SocialMediaService } from './social-media.service';

@Component({
  templateUrl: './social-media-delete-dialog.component.html',
})
export class SocialMediaDeleteDialogComponent {
  socialMedia?: ISocialMedia;

  constructor(
    protected socialMediaService: SocialMediaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.socialMediaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('socialMediaListModification');
      this.activeModal.close();
    });
  }
}

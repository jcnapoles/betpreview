import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITeamSocial } from 'app/shared/model/team-social.model';
import { TeamSocialService } from './team-social.service';

@Component({
  templateUrl: './team-social-delete-dialog.component.html',
})
export class TeamSocialDeleteDialogComponent {
  teamSocial?: ITeamSocial;

  constructor(
    protected teamSocialService: TeamSocialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.teamSocialService.delete(id).subscribe(() => {
      this.eventManager.broadcast('teamSocialListModification');
      this.activeModal.close();
    });
  }
}

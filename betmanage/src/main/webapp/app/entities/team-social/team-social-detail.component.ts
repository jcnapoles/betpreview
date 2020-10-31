import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeamSocial } from 'app/shared/model/team-social.model';

@Component({
  selector: 'jhi-team-social-detail',
  templateUrl: './team-social-detail.component.html',
})
export class TeamSocialDetailComponent implements OnInit {
  teamSocial: ITeamSocial | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teamSocial }) => (this.teamSocial = teamSocial));
  }

  previousState(): void {
    window.history.back();
  }
}

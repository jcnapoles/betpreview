import { ITeam } from 'app/shared/model/team.model';
import { ITeamSocial } from 'app/shared/model/team-social.model';
import { PlatformEnum } from 'app/shared/model/enumerations/platform-enum.model';

export interface ISocialMedia {
  id?: number;
  tag?: string;
  platform?: PlatformEnum;
  team?: ITeam;
  teamSocial?: ITeamSocial;
}

export class SocialMedia implements ISocialMedia {
  constructor(
    public id?: number,
    public tag?: string,
    public platform?: PlatformEnum,
    public team?: ITeam,
    public teamSocial?: ITeamSocial
  ) {}
}

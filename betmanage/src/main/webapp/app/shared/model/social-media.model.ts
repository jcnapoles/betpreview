import { ITeam } from 'app/shared/model/team.model';
import { PlatformEnum } from 'app/shared/model/enumerations/platform-enum.model';

export interface ISocialMedia {
  id?: number;
  tag?: string;
  platform?: PlatformEnum;
  team?: ITeam;
}

export class SocialMedia implements ISocialMedia {
  constructor(public id?: number, public tag?: string, public platform?: PlatformEnum, public team?: ITeam) {}
}

import { ISocialMedia } from 'app/shared/model/social-media.model';

export interface ITeamSocial {
  id?: number;
  homeTeamId?: number;
  visitorTeamId?: number;
  match?: string;
  socialMediaMatches?: ISocialMedia[];
}

export class TeamSocial implements ITeamSocial {
  constructor(
    public id?: number,
    public homeTeamId?: number,
    public visitorTeamId?: number,
    public match?: string,
    public socialMediaMatches?: ISocialMedia[]
  ) {}
}

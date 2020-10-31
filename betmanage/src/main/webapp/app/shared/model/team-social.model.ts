import { ISocialMedia } from 'app/shared/model/social-media.model';

export interface ITeamSocial {
  id?: number;
  home?: number;
  visitor?: number;
  match?: string;
  socialMediaMatches?: ISocialMedia[];
}

export class TeamSocial implements ITeamSocial {
  constructor(
    public id?: number,
    public home?: number,
    public visitor?: number,
    public match?: string,
    public socialMediaMatches?: ISocialMedia[]
  ) {}
}

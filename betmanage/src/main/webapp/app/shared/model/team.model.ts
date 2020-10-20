import { ICountry } from 'app/shared/model/country.model';
import { ISocialMedia } from 'app/shared/model/social-media.model';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { ICompetition } from 'app/shared/model/competition.model';

export interface ITeam {
  id?: number;
  teamName?: string;
  shortCode?: string;
  isNationalTeam?: boolean;
  teamLogoContentType?: string;
  teamLogo?: any;
  teamId?: number;
  country?: ICountry;
  socialMedias?: ISocialMedia[];
  matchPreviews?: IMatchPreview[];
  competition?: ICompetition;
}

export class Team implements ITeam {
  constructor(
    public id?: number,
    public teamName?: string,
    public shortCode?: string,
    public isNationalTeam?: boolean,
    public teamLogoContentType?: string,
    public teamLogo?: any,
    public teamId?: number,
    public country?: ICountry,
    public socialMedias?: ISocialMedia[],
    public matchPreviews?: IMatchPreview[],
    public competition?: ICompetition
  ) {
    this.isNationalTeam = this.isNationalTeam || false;
  }
}

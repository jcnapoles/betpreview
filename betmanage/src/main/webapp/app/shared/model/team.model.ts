import { ISocialMedia } from 'app/shared/model/social-media.model';
import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { ICountry } from 'app/shared/model/country.model';
import { ICompetition } from 'app/shared/model/competition.model';

export interface ITeam {
  id?: number;
  teamName?: string;
  shortCode?: string;
  isNationalTeam?: boolean;
  teamLogoContentType?: string;
  teamLogo?: any;
  teamId?: number;
  socialMedias?: ISocialMedia[];
  matchPreviewsHomes?: IMatchPreview[];
  matchPreviewsVisitors?: IMatchPreview[];
  country?: ICountry;
  competitions?: ICompetition[];
  matchPreviews?: IMatchPreview[];
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
    public socialMedias?: ISocialMedia[],
    public matchPreviewsHomes?: IMatchPreview[],
    public matchPreviewsVisitors?: IMatchPreview[],
    public country?: ICountry,
    public competitions?: ICompetition[],
    public matchPreviews?: IMatchPreview[]
  ) {
    this.isNationalTeam = this.isNationalTeam || false;
  }
}

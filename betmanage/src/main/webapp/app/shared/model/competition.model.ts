import { IMatchPreview } from 'app/shared/model/match-preview.model';
import { ITeam } from 'app/shared/model/team.model';
import { ISport } from 'app/shared/model/sport.model';
import { ICountry } from 'app/shared/model/country.model';
import { TypeCompetition } from 'app/shared/model/enumerations/type-competition.model';

export interface ICompetition {
  id?: number;
  competitionName?: string;
  competitionLogoContentType?: string;
  competitionLogo?: any;
  isCup?: boolean;
  active?: boolean;
  type?: TypeCompetition;
  sportscribeId?: number;
  matchPreviews?: IMatchPreview[];
  teams?: ITeam[];
  sport?: ISport;
  country?: ICountry;
}

export class Competition implements ICompetition {
  constructor(
    public id?: number,
    public competitionName?: string,
    public competitionLogoContentType?: string,
    public competitionLogo?: any,
    public isCup?: boolean,
    public active?: boolean,
    public type?: TypeCompetition,
    public sportscribeId?: number,
    public matchPreviews?: IMatchPreview[],
    public teams?: ITeam[],
    public sport?: ISport,
    public country?: ICountry
  ) {
    this.isCup = this.isCup || false;
    this.active = this.active || false;
  }
}

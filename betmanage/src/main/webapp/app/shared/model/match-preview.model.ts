import { Moment } from 'moment';
import { ITeam } from 'app/shared/model/team.model';
import { ITitle } from 'app/shared/model/title.model';
import { IParagraphs } from 'app/shared/model/paragraphs.model';
import { ICompetition } from 'app/shared/model/competition.model';
import { ICountry } from 'app/shared/model/country.model';
import { LanguageEnum } from 'app/shared/model/enumerations/language-enum.model';

export interface IMatchPreview {
  id?: number;
  blurbFull?: any;
  fixtureId?: number;
  hometeamId?: number;
  visitorteamId?: number;
  hometeamName?: string;
  visitorteamName?: string;
  leagueId?: number;
  league?: string;
  fixtureImgContentType?: string;
  fixtureImg?: any;
  formationImgContentType?: string;
  formationImg?: any;
  startUtcTimestamp?: Moment;
  venueName?: string;
  venueCity?: string;
  matchImgContentType?: string;
  matchImg?: any;
  matchImaTxt?: string;
  headline?: string;
  date?: Moment;
  language?: LanguageEnum;
  homeTeam?: ITeam;
  visitorTeam?: ITeam;
  titles?: ITitle[];
  paragraphs?: IParagraphs[];
  teams?: ITeam[];
  competition?: ICompetition;
  country?: ICountry;
}

export class MatchPreview implements IMatchPreview {
  constructor(
    public id?: number,
    public blurbFull?: any,
    public fixtureId?: number,
    public hometeamId?: number,
    public visitorteamId?: number,
    public hometeamName?: string,
    public visitorteamName?: string,
    public leagueId?: number,
    public league?: string,
    public fixtureImgContentType?: string,
    public fixtureImg?: any,
    public formationImgContentType?: string,
    public formationImg?: any,
    public startUtcTimestamp?: Moment,
    public venueName?: string,
    public venueCity?: string,
    public matchImgContentType?: string,
    public matchImg?: any,
    public matchImaTxt?: string,
    public headline?: string,
    public date?: Moment,
    public language?: LanguageEnum,
    public homeTeam?: ITeam,
    public visitorTeam?: ITeam,
    public titles?: ITitle[],
    public paragraphs?: IParagraphs[],
    public teams?: ITeam[],
    public competition?: ICompetition,
    public country?: ICountry
  ) {}
}

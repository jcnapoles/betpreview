import { Moment } from 'moment';
import { ICountry } from 'app/shared/model/country.model';
import { ITeam } from 'app/shared/model/team.model';
import { ITeamSocial } from 'app/shared/model/team-social.model';
import { ITitle } from 'app/shared/model/title.model';
import { IParagraphs } from 'app/shared/model/paragraphs.model';
import { IParts } from 'app/shared/model/parts.model';
import { ICompetition } from 'app/shared/model/competition.model';
import { LanguageEnum } from 'app/shared/model/enumerations/language-enum.model';

export interface IMatchPreview {
  id?: number;
  blurbFull?: string;
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
  matchImgContentType?: string;
  matchImg?: any;
  matchImaTxt?: string;
  headline?: string;
  date?: Moment;
  language?: LanguageEnum;
  country?: ICountry;
  homeTeam?: ITeam;
  visitorTeam?: ITeam;
  social?: ITeamSocial;
  titles?: ITitle[];
  paragraphs?: IParagraphs[];
  parts?: IParts[];
  competition?: ICompetition;
  teams?: ITeam[];
}

export class MatchPreview implements IMatchPreview {
  constructor(
    public id?: number,
    public blurbFull?: string,
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
    public matchImgContentType?: string,
    public matchImg?: any,
    public matchImaTxt?: string,
    public headline?: string,
    public date?: Moment,
    public language?: LanguageEnum,
    public country?: ICountry,
    public homeTeam?: ITeam,
    public visitorTeam?: ITeam,
    public social?: ITeamSocial,
    public titles?: ITitle[],
    public paragraphs?: IParagraphs[],
    public parts?: IParts[],
    public competition?: ICompetition,
    public teams?: ITeam[]
  ) {}
}

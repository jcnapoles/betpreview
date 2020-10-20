import { ICountry } from 'app/shared/model/country.model';
import { ITitle } from 'app/shared/model/title.model';
import { IParagraphs } from 'app/shared/model/paragraphs.model';
import { ITeam } from 'app/shared/model/team.model';

export interface IMatchPreview {
  id?: number;
  fixtureId?: number;
  blurbFull?: string;
  hometeamId?: number;
  visitorteamId?: number;
  hometeamName?: string;
  visitorteamName?: string;
  leagueId?: number;
  league?: string;
  formationImg?: string;
  fixtureImg?: string;
  country?: ICountry;
  titles?: ITitle[];
  paragraphs?: IParagraphs[];
  teams?: ITeam[];
}

export class MatchPreview implements IMatchPreview {
  constructor(
    public id?: number,
    public fixtureId?: number,
    public blurbFull?: string,
    public hometeamId?: number,
    public visitorteamId?: number,
    public hometeamName?: string,
    public visitorteamName?: string,
    public leagueId?: number,
    public league?: string,
    public formationImg?: string,
    public fixtureImg?: string,
    public country?: ICountry,
    public titles?: ITitle[],
    public paragraphs?: IParagraphs[],
    public teams?: ITeam[]
  ) {}
}

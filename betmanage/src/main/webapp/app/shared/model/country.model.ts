import { ICompetition } from 'app/shared/model/competition.model';
import { ITeam } from 'app/shared/model/team.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  competitions?: ICompetition[];
  teams?: ITeam[];
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public competitions?: ICompetition[], public teams?: ITeam[]) {}
}

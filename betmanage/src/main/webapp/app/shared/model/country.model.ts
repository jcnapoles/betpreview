import { ICompetition } from 'app/shared/model/competition.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  competitions?: ICompetition[];
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public competitions?: ICompetition[]) {}
}

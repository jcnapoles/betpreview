import { ICompetition } from 'app/shared/model/competition.model';

export interface ISport {
  id?: number;
  sportName?: string;
  competitions?: ICompetition[];
}

export class Sport implements ISport {
  constructor(public id?: number, public sportName?: string, public competitions?: ICompetition[]) {}
}

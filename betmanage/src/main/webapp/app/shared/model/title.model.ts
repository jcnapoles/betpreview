import { IMatchPreview } from 'app/shared/model/match-preview.model';

export interface ITitle {
  id?: number;
  titleText?: string;
  quickItems?: IMatchPreview;
}

export class Title implements ITitle {
  constructor(public id?: number, public titleText?: string, public quickItems?: IMatchPreview) {}
}

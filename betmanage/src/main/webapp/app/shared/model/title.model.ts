import { IMatchPreview } from 'app/shared/model/match-preview.model';

export interface ITitle {
  id?: number;
  titleText?: string;
  matchPreview?: IMatchPreview;
}

export class Title implements ITitle {
  constructor(public id?: number, public titleText?: string, public matchPreview?: IMatchPreview) {}
}

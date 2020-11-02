import { IMatchPreview } from 'app/shared/model/match-preview.model';

export interface IParagraphs {
  id?: number;
  content?: string;
  matchPreview?: IMatchPreview;
}

export class Paragraphs implements IParagraphs {
  constructor(public id?: number, public content?: string, public matchPreview?: IMatchPreview) {}
}

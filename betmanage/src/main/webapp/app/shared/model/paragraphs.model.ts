import { IMatchPreview } from 'app/shared/model/match-preview.model';

export interface IParagraphs {
  id?: number;
  content?: string;
  blurbSplit?: IMatchPreview;
}

export class Paragraphs implements IParagraphs {
  constructor(public id?: number, public content?: string, public blurbSplit?: IMatchPreview) {}
}

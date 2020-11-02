import { IMatchPreview } from 'app/shared/model/match-preview.model';

export interface IParts {
  id?: number;
  intro?: string;
  weather?: string;
  homeLastResult?: string;
  visitorLastResult?: string;
  homeScorers?: string;
  visitorScorers?: string;
  lastMeetingResult?: string;
  lastMeetingScoring?: string;
  homeSidelined?: string;
  visitorSidelined?: string;
  matchPreview?: IMatchPreview;
}

export class Parts implements IParts {
  constructor(
    public id?: number,
    public intro?: string,
    public weather?: string,
    public homeLastResult?: string,
    public visitorLastResult?: string,
    public homeScorers?: string,
    public visitorScorers?: string,
    public lastMeetingResult?: string,
    public lastMeetingScoring?: string,
    public homeSidelined?: string,
    public visitorSidelined?: string,
    public matchPreview?: IMatchPreview
  ) {}
}

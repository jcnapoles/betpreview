import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IMatchPreview } from 'app/shared/model/match-preview.model';

type EntityResponseType = HttpResponse<IMatchPreview>;
type EntityArrayResponseType = HttpResponse<IMatchPreview[]>;

@Injectable({ providedIn: 'root' })
export class MatchPreviewService {
  public resourceUrl = SERVER_API_URL + 'api/match-previews';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/match-previews';

  constructor(protected http: HttpClient) {}

  create(matchPreview: IMatchPreview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matchPreview);
    return this.http
      .post<IMatchPreview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(matchPreview: IMatchPreview): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matchPreview);
    return this.http
      .put<IMatchPreview>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMatchPreview>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatchPreview[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatchPreview[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(matchPreview: IMatchPreview): IMatchPreview {
    const copy: IMatchPreview = Object.assign({}, matchPreview, {
      startUtcTimestamp:
        matchPreview.startUtcTimestamp && matchPreview.startUtcTimestamp.isValid() ? matchPreview.startUtcTimestamp.toJSON() : undefined,
      date: matchPreview.date && matchPreview.date.isValid() ? matchPreview.date.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startUtcTimestamp = res.body.startUtcTimestamp ? moment(res.body.startUtcTimestamp) : undefined;
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((matchPreview: IMatchPreview) => {
        matchPreview.startUtcTimestamp = matchPreview.startUtcTimestamp ? moment(matchPreview.startUtcTimestamp) : undefined;
        matchPreview.date = matchPreview.date ? moment(matchPreview.date) : undefined;
      });
    }
    return res;
  }
load(teamId: number): Observable<EntityResponseType> {	
	return this.http.get<IMatchPreview>(`${this.resourceUrl}/loadAPIMatchPreviewByTeamId/${teamId}`, {observe: 'response' });
  }

loads(date: Date): Observable<EntityArrayResponseType> {	
	return this.http.get<IMatchPreview[]>(`${this.resourceUrl}/loadAPIMatchPreviewByDate/${date}`, {observe: 'response' });
  }
}

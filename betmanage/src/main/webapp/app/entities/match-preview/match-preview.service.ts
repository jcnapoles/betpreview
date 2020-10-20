import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IMatchPreview>(this.resourceUrl, matchPreview, { observe: 'response' });
  }

  update(matchPreview: IMatchPreview): Observable<EntityResponseType> {
    return this.http.put<IMatchPreview>(this.resourceUrl, matchPreview, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMatchPreview>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchPreview[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMatchPreview[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

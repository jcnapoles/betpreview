import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IParagraphs } from 'app/shared/model/paragraphs.model';

type EntityResponseType = HttpResponse<IParagraphs>;
type EntityArrayResponseType = HttpResponse<IParagraphs[]>;

@Injectable({ providedIn: 'root' })
export class ParagraphsService {
  public resourceUrl = SERVER_API_URL + 'api/paragraphs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/paragraphs';

  constructor(protected http: HttpClient) {}

  create(paragraphs: IParagraphs): Observable<EntityResponseType> {
    return this.http.post<IParagraphs>(this.resourceUrl, paragraphs, { observe: 'response' });
  }

  update(paragraphs: IParagraphs): Observable<EntityResponseType> {
    return this.http.put<IParagraphs>(this.resourceUrl, paragraphs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParagraphs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParagraphs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParagraphs[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

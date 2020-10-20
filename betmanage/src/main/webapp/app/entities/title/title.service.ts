import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITitle } from 'app/shared/model/title.model';

type EntityResponseType = HttpResponse<ITitle>;
type EntityArrayResponseType = HttpResponse<ITitle[]>;

@Injectable({ providedIn: 'root' })
export class TitleService {
  public resourceUrl = SERVER_API_URL + 'api/titles';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/titles';

  constructor(protected http: HttpClient) {}

  create(title: ITitle): Observable<EntityResponseType> {
    return this.http.post<ITitle>(this.resourceUrl, title, { observe: 'response' });
  }

  update(title: ITitle): Observable<EntityResponseType> {
    return this.http.put<ITitle>(this.resourceUrl, title, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITitle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITitle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITitle[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

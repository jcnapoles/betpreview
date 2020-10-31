import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IParts } from 'app/shared/model/parts.model';

type EntityResponseType = HttpResponse<IParts>;
type EntityArrayResponseType = HttpResponse<IParts[]>;

@Injectable({ providedIn: 'root' })
export class PartsService {
  public resourceUrl = SERVER_API_URL + 'api/parts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/parts';

  constructor(protected http: HttpClient) {}

  create(parts: IParts): Observable<EntityResponseType> {
    return this.http.post<IParts>(this.resourceUrl, parts, { observe: 'response' });
  }

  update(parts: IParts): Observable<EntityResponseType> {
    return this.http.put<IParts>(this.resourceUrl, parts, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParts>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParts[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParts[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

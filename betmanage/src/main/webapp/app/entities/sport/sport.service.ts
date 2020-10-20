import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISport } from 'app/shared/model/sport.model';

type EntityResponseType = HttpResponse<ISport>;
type EntityArrayResponseType = HttpResponse<ISport[]>;

@Injectable({ providedIn: 'root' })
export class SportService {
  public resourceUrl = SERVER_API_URL + 'api/sports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/sports';

  constructor(protected http: HttpClient) {}

  create(sport: ISport): Observable<EntityResponseType> {
    return this.http.post<ISport>(this.resourceUrl, sport, { observe: 'response' });
  }

  update(sport: ISport): Observable<EntityResponseType> {
    return this.http.put<ISport>(this.resourceUrl, sport, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISport[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICompetition } from 'app/shared/model/competition.model';

type EntityResponseType = HttpResponse<ICompetition>;
type EntityArrayResponseType = HttpResponse<ICompetition[]>;

@Injectable({ providedIn: 'root' })
export class CompetitionService {
  public resourceUrl = SERVER_API_URL + 'api/competitions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/competitions';

  constructor(protected http: HttpClient) {}

  create(competition: ICompetition): Observable<EntityResponseType> {
    return this.http.post<ICompetition>(this.resourceUrl, competition, { observe: 'response' });
  }

  update(competition: ICompetition): Observable<EntityResponseType> {
    return this.http.put<ICompetition>(this.resourceUrl, competition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICompetition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICompetition[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

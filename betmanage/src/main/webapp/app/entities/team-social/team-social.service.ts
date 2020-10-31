import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITeamSocial } from 'app/shared/model/team-social.model';

type EntityResponseType = HttpResponse<ITeamSocial>;
type EntityArrayResponseType = HttpResponse<ITeamSocial[]>;

@Injectable({ providedIn: 'root' })
export class TeamSocialService {
  public resourceUrl = SERVER_API_URL + 'api/team-socials';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/team-socials';

  constructor(protected http: HttpClient) {}

  create(teamSocial: ITeamSocial): Observable<EntityResponseType> {
    return this.http.post<ITeamSocial>(this.resourceUrl, teamSocial, { observe: 'response' });
  }

  update(teamSocial: ITeamSocial): Observable<EntityResponseType> {
    return this.http.put<ITeamSocial>(this.resourceUrl, teamSocial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITeamSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeamSocial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITeamSocial[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

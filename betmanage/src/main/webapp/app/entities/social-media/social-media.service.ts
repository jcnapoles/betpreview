import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISocialMedia } from 'app/shared/model/social-media.model';

type EntityResponseType = HttpResponse<ISocialMedia>;
type EntityArrayResponseType = HttpResponse<ISocialMedia[]>;

@Injectable({ providedIn: 'root' })
export class SocialMediaService {
  public resourceUrl = SERVER_API_URL + 'api/social-medias';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/social-medias';

  constructor(protected http: HttpClient) {}

  create(socialMedia: ISocialMedia): Observable<EntityResponseType> {
    return this.http.post<ISocialMedia>(this.resourceUrl, socialMedia, { observe: 'response' });
  }

  update(socialMedia: ISocialMedia): Observable<EntityResponseType> {
    return this.http.put<ISocialMedia>(this.resourceUrl, socialMedia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISocialMedia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocialMedia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocialMedia[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}

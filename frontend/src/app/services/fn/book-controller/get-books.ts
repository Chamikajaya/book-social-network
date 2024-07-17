/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseBookResponseBody } from '../../models/page-response-book-response-body';

export interface GetBooks$Params {
  page?: number;
  size?: number;
}

export function getBooks(http: HttpClient, rootUrl: string, params?: GetBooks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponseBody>> {
  const rb = new RequestBuilder(rootUrl, getBooks.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseBookResponseBody>;
    })
  );
}

getBooks.PATH = '/books';

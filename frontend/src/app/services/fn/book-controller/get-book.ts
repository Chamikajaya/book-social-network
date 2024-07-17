/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { BookResponseBody } from '../../models/book-response-body';

export interface GetBook$Params {
  id: number;
}

export function getBook(http: HttpClient, rootUrl: string, params: GetBook$Params, context?: HttpContext): Observable<StrictHttpResponse<BookResponseBody>> {
  const rb = new RequestBuilder(rootUrl, getBook.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<BookResponseBody>;
    })
  );
}

getBook.PATH = '/books/{id}';

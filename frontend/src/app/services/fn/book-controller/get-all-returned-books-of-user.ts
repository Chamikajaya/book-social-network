/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PageResponseBorrowedBookResponseBody } from '../../models/page-response-borrowed-book-response-body';

export interface GetAllReturnedBooksOfUser$Params {
  page?: number;
  size?: number;
}

export function getAllReturnedBooksOfUser(http: HttpClient, rootUrl: string, params?: GetAllReturnedBooksOfUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBookResponseBody>> {
  const rb = new RequestBuilder(rootUrl, getAllReturnedBooksOfUser.PATH, 'get');
  if (params) {
    rb.query('page', params.page, {});
    rb.query('size', params.size, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<PageResponseBorrowedBookResponseBody>;
    })
  );
}

getAllReturnedBooksOfUser.PATH = '/books/returned';

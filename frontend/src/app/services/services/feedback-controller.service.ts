/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createFeedback } from '../fn/feedback-controller/create-feedback';
import { CreateFeedback$Params } from '../fn/feedback-controller/create-feedback';
import { getAllFeedbacksByBook } from '../fn/feedback-controller/get-all-feedbacks-by-book';
import { GetAllFeedbacksByBook$Params } from '../fn/feedback-controller/get-all-feedbacks-by-book';
import { PageResponseFeedbackResponseBody } from '../models/page-response-feedback-response-body';

@Injectable({ providedIn: 'root' })
export class FeedbackControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `createFeedback()` */
  static readonly CreateFeedbackPath = '/feedback';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createFeedback()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedback$Response(params: CreateFeedback$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return createFeedback(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createFeedback$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createFeedback(params: CreateFeedback$Params, context?: HttpContext): Observable<void> {
    return this.createFeedback$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `getAllFeedbacksByBook()` */
  static readonly GetAllFeedbacksByBookPath = '/feedback/book/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllFeedbacksByBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFeedbacksByBook$Response(params: GetAllFeedbacksByBook$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseFeedbackResponseBody>> {
    return getAllFeedbacksByBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllFeedbacksByBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllFeedbacksByBook(params: GetAllFeedbacksByBook$Params, context?: HttpContext): Observable<PageResponseFeedbackResponseBody> {
    return this.getAllFeedbacksByBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseFeedbackResponseBody>): PageResponseFeedbackResponseBody => r.body)
    );
  }

}

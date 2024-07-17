/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { addBook } from '../fn/book-controller/add-book';
import { AddBook$Params } from '../fn/book-controller/add-book';
import { approveTheReturnOfBorrowedBook } from '../fn/book-controller/approve-the-return-of-borrowed-book';
import { ApproveTheReturnOfBorrowedBook$Params } from '../fn/book-controller/approve-the-return-of-borrowed-book';
import { BookResponseBody } from '../models/book-response-body';
import { borrowABook } from '../fn/book-controller/borrow-a-book';
import { BorrowABook$Params } from '../fn/book-controller/borrow-a-book';
import { getAllBooksOfAOwner } from '../fn/book-controller/get-all-books-of-a-owner';
import { GetAllBooksOfAOwner$Params } from '../fn/book-controller/get-all-books-of-a-owner';
import { getAllBorrowedBooksOfUser } from '../fn/book-controller/get-all-borrowed-books-of-user';
import { GetAllBorrowedBooksOfUser$Params } from '../fn/book-controller/get-all-borrowed-books-of-user';
import { getAllReturnedBooksOfUser } from '../fn/book-controller/get-all-returned-books-of-user';
import { GetAllReturnedBooksOfUser$Params } from '../fn/book-controller/get-all-returned-books-of-user';
import { getBook } from '../fn/book-controller/get-book';
import { GetBook$Params } from '../fn/book-controller/get-book';
import { getBooks } from '../fn/book-controller/get-books';
import { GetBooks$Params } from '../fn/book-controller/get-books';
import { PageResponseBookResponseBody } from '../models/page-response-book-response-body';
import { PageResponseBorrowedBookResponseBody } from '../models/page-response-borrowed-book-response-body';
import { returnTheBorrowedBook } from '../fn/book-controller/return-the-borrowed-book';
import { ReturnTheBorrowedBook$Params } from '../fn/book-controller/return-the-borrowed-book';
import { updateArchivedStatus } from '../fn/book-controller/update-archived-status';
import { UpdateArchivedStatus$Params } from '../fn/book-controller/update-archived-status';
import { updateShareableStatus } from '../fn/book-controller/update-shareable-status';
import { UpdateShareableStatus$Params } from '../fn/book-controller/update-shareable-status';
import { uploadBookCoverImg } from '../fn/book-controller/upload-book-cover-img';
import { UploadBookCoverImg$Params } from '../fn/book-controller/upload-book-cover-img';

@Injectable({ providedIn: 'root' })
export class BookControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getBooks()` */
  static readonly GetBooksPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBooks()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBooks$Response(params?: GetBooks$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponseBody>> {
    return getBooks(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBooks$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBooks(params?: GetBooks$Params, context?: HttpContext): Observable<PageResponseBookResponseBody> {
    return this.getBooks$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponseBody>): PageResponseBookResponseBody => r.body)
    );
  }

  /** Path part for operation `addBook()` */
  static readonly AddBookPath = '/books';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addBook()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addBook$Response(params: AddBook$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return addBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `addBook$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  addBook(params: AddBook$Params, context?: HttpContext): Observable<void> {
    return this.addBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `uploadBookCoverImg()` */
  static readonly UploadBookCoverImgPath = '/books/cover/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `uploadBookCoverImg()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverImg$Response(params: UploadBookCoverImg$Params, context?: HttpContext): Observable<StrictHttpResponse<{
}>> {
    return uploadBookCoverImg(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `uploadBookCoverImg$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  uploadBookCoverImg(params: UploadBookCoverImg$Params, context?: HttpContext): Observable<{
}> {
    return this.uploadBookCoverImg$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
}>): {
} => r.body)
    );
  }

  /** Path part for operation `borrowABook()` */
  static readonly BorrowABookPath = '/books/borrow/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `borrowABook()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowABook$Response(params: BorrowABook$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return borrowABook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `borrowABook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  borrowABook(params: BorrowABook$Params, context?: HttpContext): Observable<void> {
    return this.borrowABook$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `updateShareableStatus()` */
  static readonly UpdateShareableStatusPath = '/books/shareable/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateShareableStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateShareableStatus$Response(params: UpdateShareableStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return updateShareableStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateShareableStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateShareableStatus(params: UpdateShareableStatus$Params, context?: HttpContext): Observable<void> {
    return this.updateShareableStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `returnTheBorrowedBook()` */
  static readonly ReturnTheBorrowedBookPath = '/books/borrow/return/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `returnTheBorrowedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnTheBorrowedBook$Response(params: ReturnTheBorrowedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return returnTheBorrowedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `returnTheBorrowedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  returnTheBorrowedBook(params: ReturnTheBorrowedBook$Params, context?: HttpContext): Observable<void> {
    return this.returnTheBorrowedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `approveTheReturnOfBorrowedBook()` */
  static readonly ApproveTheReturnOfBorrowedBookPath = '/books/borrow/return/approve/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `approveTheReturnOfBorrowedBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveTheReturnOfBorrowedBook$Response(params: ApproveTheReturnOfBorrowedBook$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return approveTheReturnOfBorrowedBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `approveTheReturnOfBorrowedBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  approveTheReturnOfBorrowedBook(params: ApproveTheReturnOfBorrowedBook$Params, context?: HttpContext): Observable<void> {
    return this.approveTheReturnOfBorrowedBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `updateArchivedStatus()` */
  static readonly UpdateArchivedStatusPath = '/books/archived/{bookId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateArchivedStatus()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchivedStatus$Response(params: UpdateArchivedStatus$Params, context?: HttpContext): Observable<StrictHttpResponse<void>> {
    return updateArchivedStatus(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateArchivedStatus$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateArchivedStatus(params: UpdateArchivedStatus$Params, context?: HttpContext): Observable<void> {
    return this.updateArchivedStatus$Response(params, context).pipe(
      map((r: StrictHttpResponse<void>): void => r.body)
    );
  }

  /** Path part for operation `getBook()` */
  static readonly GetBookPath = '/books/{id}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBook()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBook$Response(params: GetBook$Params, context?: HttpContext): Observable<StrictHttpResponse<BookResponseBody>> {
    return getBook(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getBook$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBook(params: GetBook$Params, context?: HttpContext): Observable<BookResponseBody> {
    return this.getBook$Response(params, context).pipe(
      map((r: StrictHttpResponse<BookResponseBody>): BookResponseBody => r.body)
    );
  }

  /** Path part for operation `getAllReturnedBooksOfUser()` */
  static readonly GetAllReturnedBooksOfUserPath = '/books/returned';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllReturnedBooksOfUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllReturnedBooksOfUser$Response(params?: GetAllReturnedBooksOfUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBookResponseBody>> {
    return getAllReturnedBooksOfUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllReturnedBooksOfUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllReturnedBooksOfUser(params?: GetAllReturnedBooksOfUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBookResponseBody> {
    return this.getAllReturnedBooksOfUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBookResponseBody>): PageResponseBorrowedBookResponseBody => r.body)
    );
  }

  /** Path part for operation `getAllBooksOfAOwner()` */
  static readonly GetAllBooksOfAOwnerPath = '/books/owner';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllBooksOfAOwner()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllBooksOfAOwner$Response(params?: GetAllBooksOfAOwner$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBookResponseBody>> {
    return getAllBooksOfAOwner(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllBooksOfAOwner$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllBooksOfAOwner(params?: GetAllBooksOfAOwner$Params, context?: HttpContext): Observable<PageResponseBookResponseBody> {
    return this.getAllBooksOfAOwner$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBookResponseBody>): PageResponseBookResponseBody => r.body)
    );
  }

  /** Path part for operation `getAllBorrowedBooksOfUser()` */
  static readonly GetAllBorrowedBooksOfUserPath = '/books/borrowed';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllBorrowedBooksOfUser()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllBorrowedBooksOfUser$Response(params?: GetAllBorrowedBooksOfUser$Params, context?: HttpContext): Observable<StrictHttpResponse<PageResponseBorrowedBookResponseBody>> {
    return getAllBorrowedBooksOfUser(this.http, this.rootUrl, params, context);
  }

  /**
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllBorrowedBooksOfUser$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllBorrowedBooksOfUser(params?: GetAllBorrowedBooksOfUser$Params, context?: HttpContext): Observable<PageResponseBorrowedBookResponseBody> {
    return this.getAllBorrowedBooksOfUser$Response(params, context).pipe(
      map((r: StrictHttpResponse<PageResponseBorrowedBookResponseBody>): PageResponseBorrowedBookResponseBody => r.body)
    );
  }

}

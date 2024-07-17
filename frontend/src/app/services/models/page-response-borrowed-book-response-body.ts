/* tslint:disable */
/* eslint-disable */
import { BorrowedBookResponseBody } from '../models/borrowed-book-response-body';
export interface PageResponseBorrowedBookResponseBody {
  content?: Array<BorrowedBookResponseBody>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}

/* tslint:disable */
/* eslint-disable */
import { BookResponseBody } from '../models/book-response-body';
export interface PageResponseBookResponseBody {
  content?: Array<BookResponseBody>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}

/* tslint:disable */
/* eslint-disable */
import { FeedbackResponseBody } from '../models/feedback-response-body';
export interface PageResponseFeedbackResponseBody {
  content?: Array<FeedbackResponseBody>;
  first?: boolean;
  last?: boolean;
  number?: number;
  size?: number;
  totalElements?: number;
  totalPages?: number;
}

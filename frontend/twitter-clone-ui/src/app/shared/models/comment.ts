import { Media } from './media';
import { Tweet } from './tweet';

export class Comment {
  commentId!: string;
  commentUrl!: string;
  username!: string;
  tweet!: Tweet;
  media?: Media;
  content!: string;
  dateOfCreation!: Date;
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
}

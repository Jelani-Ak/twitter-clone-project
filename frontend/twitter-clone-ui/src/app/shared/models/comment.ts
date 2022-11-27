import { Media } from './media';

export class Comment {
  commentId!: string;
  parentTweetId!: string;
  username!: string;
  media?: Media;
  content!: string;
  dateOfCreation!: Date;
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
}

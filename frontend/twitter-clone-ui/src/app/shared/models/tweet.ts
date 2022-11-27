import { Media } from './media';
import { Comment } from './comment';

export class Tweet {
  tweetId!: string;
  username!: string;
  media?: Media;
  content!: string;
  dateOfCreation!: Date;
  comments!: Array<Comment>;
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
  tweetType!: TweetType;
}

enum TweetType {
  TWEET,
  RETWEET,
  MEDIA,
  LIKED,
}

import { Media } from '../media/media';
import { Comment } from '../comment/comment';

export class Tweet {
  tweetId!: string;
  tweetUrl!: string;
  username!: string;
  media?: Media;
  content!: string;
  dateOfCreation!: Date;
  comments!: Set<Comment>;
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

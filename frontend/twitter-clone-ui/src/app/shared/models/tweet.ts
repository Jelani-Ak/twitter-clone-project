import { Media } from './media';

export class Tweet {
  tweetId!: string;
  username!: string;
  media?: Media;
  content?: string;
  dateOfCreation!: Date;
  comments!: Array<Comment>;
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
  tweetType!: TweetType;
}

export class Comment {
  commentId!: string;
  parentTweetId!: string;
  username!: string;
  media?: Media;
  content?: string;
  dateOfCreation!: Date;
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
  tweetType!: TweetType;
}

export enum TweetType {
  TWEET = 'Tweet',
  RETWEET = 'Retweet',
  MEDIA = 'Media',
  LIKED = 'Liked',
  COMMENT = 'Comment'
}

import { Media, MediaDTO } from './media';

export type CreateTweetDTO = {
  tweet: Tweet | Comment;
  file: File | null;
};

export type TweetAndMediaDTO = {
  tweetDTO: TweetDTO;
  mediaDTO: MediaDTO;
};

export type CommentAndMediaDTO = {
  commentDTO: CommentDTO;
  mediaDTO: MediaDTO;
};

export type CommentDTO = {
  parentTweetId: string;
  commentId: string;
  userId: string;
};

export type TweetDTO = {
  tweetId: string;
  userId: string;
};

export class Tweet {
  tweetId!: string;
  userId!: string;
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
  userId!: string;
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
  COMMENT = 'Comment',
}

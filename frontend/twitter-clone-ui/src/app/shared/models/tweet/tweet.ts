import {Media} from "../media/media";
import {Comment} from "../comment/comment";

export class Tweet extends Media {
  tweetId?: number;
  tweetUrl!: string;
  username!: string;
  media?: Media;
  content!: string;
  createdDate!: string;
  comment!: Array<Comment>
  commentCount!: number;
  retweetCount!: number;
  likeCount!: number;
  tweetType!: TweetType;
}

enum TweetType {
  TWEET,
  RETWEET,
  MEDIA,
  LIKED
}

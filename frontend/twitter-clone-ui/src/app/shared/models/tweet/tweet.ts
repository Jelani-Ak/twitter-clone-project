export interface Tweet {
  tweetId?: number;
  username: string;
  tweetUrl: string;
  content: string;
  commentCount: number;
  retweetCount: number;
  createdDate: string;
  likeCount: number;
}

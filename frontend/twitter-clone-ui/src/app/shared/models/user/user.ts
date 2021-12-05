export interface User {
  userId?: number;
  username: string;
  password: string;
  email: string;
  displayName: string;
  userHandle: string;
  bioText: string;
  bioLocation: string;
  bioExternalLink: string;
  createdDate: string;
  pictureAvatarUrl: string;
  pictureBackgroundUrl: string;
  follow: string;
  tweetCount: number;
  tweetQuoteCount: number;
  enabled: boolean;
}

import { Tweet } from './tweet';

export class User {
  userId!: string;
  username!: string;
  password!: string;

  email!: string;
  displayName?: string;
  userHandle?: string;
  bioText?: string;
  bioLocation?: string;
  bioExternalLink?: string;
  
  roles!: Array<string>;

  dateOfCreation!: string;
  pictureAvatarUrl?: string;
  pictureBackgroundUrl?: string;

  following?: Set<User>;
  followers?: Set<User>;
  followersMutual?: Set<User>;
  tweets?: Set<Tweet>;

  tweetCount?: number;
  tweetQuoteCount?: number;

  follow?: boolean;
  verified?: boolean;
  locked?: boolean;
  enabled?: boolean;
}

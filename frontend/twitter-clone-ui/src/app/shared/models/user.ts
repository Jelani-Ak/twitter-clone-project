import { Tweet } from './tweet';

export class User {
  userId!: string;
  username!: string;
  password!: string;

  email!: string;
  displayName?: string;
  userHandleName?: string;
  biobioAboutTextText?: string;
  bioLocation?: string;
  bioExternalLink?: string;
  
  roles!: Array<string>;

  dateOfCreation!: string;
  pictureAvatarUrl?: string;
  pictureBackgroundUrl?: string;

  usersYouFollow?: Set<User>;
  usersFollowingYou?: Set<User>;
  mutualFollowers?: Set<User>;
  tweets?: Set<Tweet>;

  tweetCount?: number;
  tweetQuoteCount?: number;

  following?: boolean;
  verified?: boolean;
  locked?: boolean;
  enabled?: boolean;
}

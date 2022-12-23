import { Tweet } from './tweet';

export type UserInformation = {
  username: string;
  userHandleName: string;
};

export class User {
  userId!: string;
  username!: string;
  password!: string;

  email!: string;
  displayName?: string;
  userHandleName?: string;
  biobioAboutText?: string;
  bioLocation?: string;
  bioExternalLink?: string;

  roles!: Set<string>;

  dateOfCreation!: string;
  pictureAvatarUrl?: string;
  pictureBackgroundUrl?: string;

  usersYouFollow?: Set<User>;
  usersFollowingYou?: Set<User>;
  mutualFollowers?: Set<User>;
  tweets!: Set<Tweet>;
  comments!: Set<Comment>;

  tweetCount!: number;
  commentCount!: number;
  tweetQuoteCount?: number;

  following?: boolean;
  verified?: boolean;
  locked?: boolean;
  enabled?: boolean;
}

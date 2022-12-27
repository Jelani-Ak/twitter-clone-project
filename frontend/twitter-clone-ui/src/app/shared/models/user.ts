import { Tweet } from './tweet';

export type UserProfileDTO = {
  userId: string;
  username: string;
  password: string;
  email: string;
  displayName: string;
  userHandleName: string;
  bioAboutText: string;
  bioLocation: string;
  bioExternalLink: string;
};

export type UserInformation = {
  username: string;
  userHandleName: string;
};

export class User {
  userId!: string;
  username!: string;
  password!: string;

  email!: string;
  displayName!: string;
  userHandleName!: string;
  bioAboutText!: string;
  bioLocation!: string;
  bioExternalLink!: string;

  roles!: Set<string>;

  dateOfCreation!: string;
  pictureAvatarUrl?: string;
  pictureBackgroundUrl?: string;

  usersYouFollow!: Set<User>;
  usersFollowingYou!: Set<User>;
  mutualFollowers!: Set<User>;
  tweets!: Set<Tweet>;
  likedTweets!: Set<Tweet>;
  comments!: Set<Comment>;
  likedComments!: Set<Comment>;

  usersYouFollowCount!: number;
  usersFollowingYouCount!: number;
  mutualFollowersCount!: number;

  tweetCount!: number;
  commentCount!: number;
  tweetQuoteCount!: number;

  following!: boolean; // TODO: - Remove
  verified!: boolean;
  locked!: boolean;
  enabled!: boolean;
}

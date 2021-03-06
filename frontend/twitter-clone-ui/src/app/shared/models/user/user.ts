import {Tweet} from "../tweet/tweet";

export class User {
  userId!: number;
  username!: string;
  password!: string;
  email!: string;
  displayName?: string;
  userHandle?: string;
  bioText?: string;
  bioLocation?: string;
  bioExternalLink?: string;
  createdDate!: string;
  pictureAvatarUrl?: string;
  pictureBackgroundUrl?: string;

  following?:Array<User>;
  followers?:Array<User>;
  followersMutual?:Array<User>;
  tweets?:Array<Tweet>;

  tweetCount?: number;
  tweetQuoteCount?: number;

  follow?: boolean;
  enabled?: boolean;

  constructor() {
  }
}

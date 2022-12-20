import { Injectable } from '@angular/core';
import { UserInformation } from 'src/app/shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class UserCacheService {
  private userCache: Map<string, UserInformation> = new Map();

  constructor() {}

  public store(userId: string, user: UserInformation) {
    this.userCache.set(userId, user);
  }

  public getUser(userId: string) {
    this.userCache.get(userId);
  }

  // TODO: Cache user information or lazy load to reduce API calls? 
  // (Limit the number of tweets to what's viewable on the page)
}

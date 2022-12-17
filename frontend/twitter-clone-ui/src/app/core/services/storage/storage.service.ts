import { Injectable } from '@angular/core';
import { User } from 'src/app/shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class StorageService {
  private readonly USER_KEY = 'auth-user';

  public clear(): void {
    window.sessionStorage.clear();
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(this.USER_KEY);
    window.sessionStorage.setItem(this.USER_KEY, JSON.stringify(user));
  }

  public getUser() {
    const user = window.sessionStorage.getItem(this.USER_KEY);
    return user ? JSON.parse(user) : {};
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(this.USER_KEY);
    return user ? true : false;
  }
}

import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../services/storage/storage.service';

export type AuthenticatedUser = {
  userId: string;
  roles: Array<string>;
};

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  public currentUser: any;

  constructor(private storageServive: StorageService) {
    this.currentUser = this.storageServive.getUser();
    console.log('Session started');
  }

  public isAdmin(): boolean {
    const isAdmin = this.currentUser.roles?.includes('Admin');
    return isAdmin;
  }

  public isLoggedIn(): boolean {
    const isLoggedIn = this.storageServive.isLoggedIn();
    return isLoggedIn;
  }
}

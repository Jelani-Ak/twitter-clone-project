import { Component } from '@angular/core';
import { StorageService } from '../../services/storage/storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent {
  public get currentUser(): any {
    return this.storageService.getUser();
  }

  constructor(private storageService: StorageService) {
    console.log('Session started');
  }

  public isAdmin(): boolean {
    const isAdmin = this.currentUser.roles?.includes('Admin');
    return isAdmin;
  }

  public isLoggedIn(): boolean {
    const isLoggedIn = this.storageService.isLoggedIn();
    return isLoggedIn;
  }
}

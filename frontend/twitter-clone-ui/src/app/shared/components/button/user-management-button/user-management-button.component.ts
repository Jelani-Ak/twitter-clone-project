import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { StorageService } from 'src/app/core/services/storage/storage.service';

@Component({
  selector: 'app-user-management-button',
  templateUrl: './user-management-button.component.html',
  styleUrls: ['./user-management-button.component.css'],
})
export class UserManagementButtonComponent implements OnInit {
  @Input() public text: string = '';

  public get currentUser(): any {
    return this.storageService.getUser();
  }

  constructor(
    private router: Router,
    private storageService: StorageService,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  public ngOnInit(): void {
    const twitterCloneButton = this.text == 'Twitter Clone';
    if (twitterCloneButton) {
      document.getElementById('user-management-button')!.style.fontSize = '17.5px';
    }
  }

  public navigate() {
    const location = this.text.toLowerCase();
    switch (location) {
      case `${this.currentUser?.username?.toLowerCase()}`:
        const userId = this.currentUser.id;
        const username = this.currentUser.username;
        this.goToProfile(userId, username);
        break;
      case 'logout':
        this.logout();
        break;
      case 'twitter clone':
        this.router.navigate(['home']);
        break;
      default:
        this.router.navigate([`${location}`]);
        break;
    }
  }

  public goToProfile(userId: string, username: string) {
    this.router.navigate([`/profile/${username}`], {
      queryParams: { userId: userId },
    });
  }

  private logout() {
    this.storageService.clear();
    this.authenticationService.logUserOutFromRemote().subscribe({
      complete: () => {
        console.log('Logout succesful');
        this.snackbarService.displayToast('Logout successful');
        this.router.navigate(['login']);
      },
      error: (error) => {
        console.error('Failed to logout', error);
      },
    });
  }
}

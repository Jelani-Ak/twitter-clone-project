import { Component, Input, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { StorageService } from 'src/app/core/services/storage/storage.service';

@Component({
  selector: 'app-user-management-button',
  templateUrl: './user-management-button.component.html',
  styleUrls: ['./user-management-button.component.css'],
})
export class UserManagementButtonComponent implements OnInit {
  @Input() public text: string = '';

  constructor(
    private navigation: NavigationService,
    private storageService: StorageService,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  public ngOnInit(): void {
    const twitterCloneButton = this.text == 'Twitter Clone'
    if (twitterCloneButton) {
      document.getElementById('user-management-button')!.style.fontSize = '17.5px';
    }
  }

  public navigate() {
    const location = this.text;
    switch (location) {
      case 'Logout':
        this.logout();
        break;
      case 'Twitter Clone':
        this.goToUrl('Home');
        break;
      default:
        this.goToUrl(location);
        break;
    }
  }

  private goToUrl(location: string) {
    this.navigation.navigate(location);
  }

  private logout() {
    this.storageService.clear();
    this.authenticationService.logUserOutFromRemote().subscribe({
      complete: () => {
        console.log('Logout succesful');
        this.snackbarService.displayToast('Logout successful', 'ok');
        this.goToUrl('Login');
      },
      error: (error) => {
        console.error('Failed to logout', error);
      },
    });
  }
}

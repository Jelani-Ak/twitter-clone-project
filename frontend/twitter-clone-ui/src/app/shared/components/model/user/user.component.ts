import { Component } from '@angular/core';
import { AdminService } from 'src/app/core/services/admin/admin.service';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { UtilityService } from 'src/app/core/services/utility/utility.service';
import { ConfirmationToken } from 'src/app/shared/models/confirmationToken';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  users: User[] = [];
  confirmationTokens: ConfirmationToken[] = [];

  userColumnsToDisplay = [
    'userId',
    'username',
    'email'
  ];

  confirmationTokenColumnsToDisplay = [
    'token',
    'createdAt',
    'expiresAt',
    'confirmedAt'
  ];

  constructor(
    private adminService: AdminService,
    private utilityService: UtilityService,
    private authenticationService: AuthenticationService
  ) {
    this.setup();
  }

  private setup(): void {
    this.getUsers();
    this.getConfirmationTokens();
  }

  private getUsers() {
    this.adminService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      complete: () => {
        console.log(`Loaded all users`);
      },
      error: (error) => {
        console.error(`Failed to load all users`, error);
      },
    });
  }

  private getConfirmationTokens() {
    this.adminService.getAllConfirmationTokens().subscribe({
      next: (confirmationTokens) => {
        this.confirmationTokens = confirmationTokens;
      },
      complete: () => {
        console.log(`Loaded all confirmation tokens`);
      },
      error: (error) => {
        console.warn(`Faled to load cofirmaion tokens`, error);
      },
    });
  }

  public deleteUser(user: User) {
    this.users = this.users.filter((userIndex) => userIndex != user);
    this.adminService.deleteUserFromRemote(user.userId).subscribe({
      complete: () => {
        console.log(`${user.username} deleted`);
      },
      error: (error) => {
        console.warn(`Faled to delete ${user.username}`, error);
      },
    });
  }

  public confirmUser(token: string) {
    this.authenticationService.confirmUser(token).subscribe({
      complete: () => {
        console.log('Token confirmed');
      },
      error: (error) => {
        console.warn('Failed to confirm token', error);
      },
    });
  }

  public rowInformation(column: string, row: { [index: string]: any }) {
    return row[column];
  }

  public capitaliseAndSpace(text: string) {
    return this.utilityService.capitaliseAndSpace(text);
  }
}

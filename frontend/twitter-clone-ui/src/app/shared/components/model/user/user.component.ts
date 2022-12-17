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
  public users: User[] = [];
  public confirmationTokens: ConfirmationToken[] = [];

  public USER_COLUMNS: string[] = ['userId', 'username', 'email'];
  public TOKEN_COLUMNS: string[] = ['token', 'createdAt', 'expiresAt', 'confirmedAt'];

  constructor(
    private adminService: AdminService,
    private utilityService: UtilityService,
    private authenticationService: AuthenticationService
  ) {
    this.setup();
  }

  private setup(): void {
    this.getAllUsers();
    this.getAllConfirmationTokens();
  }

  private getAllUsers(): void {
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

  private getAllConfirmationTokens(): void {
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

  public deleteUser(user: User): void {
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

  public confirmUser(token: string): void {
    this.authenticationService.confirmUserFromRemote(token).subscribe({
      complete: () => {
        console.log('Token confirmed');
      },
      error: (error) => {
        console.warn(error.error.message, error);
      },
    });
  }

  public deleteToken(token: string): void {
    this.confirmationTokens = this.confirmationTokens.filter(
      (tokenIndex) => tokenIndex.token != token
    );
    this.authenticationService.deleteTokenFromRemote(token).subscribe({
      complete: () => {
        console.log('Token deleted');
      },
      error: (error) => {
        console.warn(error.error.message, error);
      },
    });
  }

  public rowInformation(
    column: string,
    row: { [index: string]: string }
  ): string {
    return row[column];
  }

  public capitaliseAndSpace(columnText: string): string {
    return this.utilityService.capitaliseAndSpace(columnText);
  }
}

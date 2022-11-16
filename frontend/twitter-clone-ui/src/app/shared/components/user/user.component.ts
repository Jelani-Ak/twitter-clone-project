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
    'email',
  ];

  confirmationTokenColumnsToDisplay = [
    'token',
    'createdAt',
    'expiresAt',
    'confirmedAt',
  ];

  constructor(
    private adminService: AdminService,
    private utilityService: UtilityService,
    private authenticationService: AuthenticationService
  ) {
    this.setup();
  }

  setup(): void {
    this.getUsers();
    this.getConfirmationTokens();
  }

  getUsers() {
    this.adminService
      .getAllUsers()
      .subscribe((users) => {
        this.users = users;
      });
  }

  getConfirmationTokens() {
    this.adminService
      .getAllConfirmationTokens()
      .subscribe((confirmationTokens) => {
        this.confirmationTokens = confirmationTokens;
      });
  }

  deleteUser(user: User) {
    this.users = this.users.filter((userIndex) => userIndex != user);
    this.adminService.deleteUserFromRemote(user.userId).subscribe({
      complete: () => { console.log(`${user} deleted`) },
      error: () => { console.warn(`Faled to delete ${user}`) }
    });
  }

  confirmUser(token: string) {
    this.authenticationService.confirmUser(token).subscribe({
      complete: () => { console.log('Token confirmed') },
      error: () => { console.warn("Token already confirmed") }
    });
  }

  rowInformation(column: string, row: { [index: string]: any }) {
    return row[column];
  }

  capitaliseAndSpace(text: string) {
    return this.utilityService.capitaliseAndSpace(text);
  }
}

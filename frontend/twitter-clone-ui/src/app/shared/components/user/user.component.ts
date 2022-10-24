import { Component } from '@angular/core';
import { AdminService } from 'src/app/core/services/admin/admin.service';
import { AuthenticationService } from 'src/app/core/services/authentication/authentication.service';
import { ConfirmationToken } from '../../models/confirmationToken';
import { User } from '../../models/user';

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
    // 'password',
    'email',
  ];
  userColumnNames = {
    userId: 'User ID',
    username: 'Username',
    password: 'Password',
    email: 'E-mail',
  };

  confirmationTokenColumnsToDisplay = [
    'token',
    'createdAt',
    'expiresAt',
    'confirmedAt',
  ];
  confirmationTokenColumnNames = {
    token: 'Token',
    createdAt: 'Created At',
    expiresAt: 'Expired At',
    confirmedAt: 'Confirmed At',
  };

  constructor(
    private adminService: AdminService, 
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
    this.adminService.deleteUserFromRemote(user.userId).subscribe();
  }

  confirmUser(token: string){
    this.authenticationService.confirmUser(token).subscribe();
  }

  rowInformation(column: string, row: { [index: string]: any }) {
    return row[column];
  }

  translateColumnName(columnName: string): string {
    const uesrname = this.userColumnNames[columnName as keyof typeof this.userColumnNames];
    const token = this.confirmationTokenColumnNames[columnName as keyof typeof this.confirmationTokenColumnNames];

    if (uesrname) return uesrname;
    if (token) return token;

    return columnName;
  }
}

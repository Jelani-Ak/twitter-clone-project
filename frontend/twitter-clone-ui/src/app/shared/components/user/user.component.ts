import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/core/services/admin/admin.service';
import { UserService } from '../../../core/services/user/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  users: User[] = [];
  columnsToDisplay = [
    'userId',
    'username',
    // 'password',
    'email',
  ];
  columnNames = {
    userId: 'User ID',
    username: 'Username',
    password: 'Password',
    email: 'E-mail',
  };

  constructor(private adminService: AdminService) {
    this.setup();
  }

  setup(): void {
    this.getUsers();
  }

  getUsers() {
    this.adminService.getAllUsers().subscribe((users) => {
      this.users = users;
    });
  }

  deleteUser(user: User) {
    this.users = this.users.filter((userIndex) => userIndex != user);
    this.adminService.deleteUserFromRemote(user.userId).subscribe();
  }

  rowInformation(column: string, row: { [index: string]: any }) {
    return row[column];
  }

  translateColumnName(columnName: string): string {
    const found = this.columnNames[columnName as keyof typeof this.columnNames];

    if (found) {
      return found;
    }

    return columnName;
  }
}

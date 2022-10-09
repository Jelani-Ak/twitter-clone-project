import { Component, OnInit } from '@angular/core';
import { UserService } from '../../../core/services/user/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent {
  users: User[] = [];
  columnsToDisplay = ['userId', 'username', 'password', 'email'];
  columnNames = {
    userId: 'User ID',
    username: 'Username',
    password: 'Password',
    email: 'E-mail',
  };

  constructor(private userService: UserService) {
    this.setup();
  }

  setup(): void {
    this.getUsers();
  }

  getUsers() {
    this.userService.getAllUsers().subscribe((users) => {
      this.users = users;
    });
  }

  rowInformation(column: string | number, row: { [x: string]: any }) {
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

import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/http/user/user.service";
import {User} from "./user";

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
    users: User[] = [];
    columnsToDisplay = [
        'userId',
        'username',
        'password',
        'email'
    ];

    constructor(private userService: UserService) {

    }

    ngOnInit(): void {
        this.userService.getAllUsers().subscribe((users) => {
            console.log(users);
            this.users = users;
        });
    }
}

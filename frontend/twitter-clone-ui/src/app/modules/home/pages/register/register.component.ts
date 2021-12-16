import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../../../shared/models/user/user";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    user = new User();

    constructor(
        private router: Router
    ) {
    }

    ngOnInit(): void {
    }


    registerUser() {

    }

    goToLoginPage() {

    }
}

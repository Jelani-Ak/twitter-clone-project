import {Component, OnInit} from '@angular/core';
import {RegistrationService} from "../../../../core/services/registration/registration.service";
import {User} from "../../../../shared/models/user/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  user = new User();

  constructor(
    private router: Router,
    private registrationService: RegistrationService
  ) {
  }

  ngOnInit(): void {
  }

  loginUser() {
    this.registrationService.logUserInFromRemote(this.user).subscribe(
      data => {
        console.log("Login Successful");
        setTimeout(() => {
          this.router.navigateByUrl("/home");
        }, 2000);
      }
    );
  }
}

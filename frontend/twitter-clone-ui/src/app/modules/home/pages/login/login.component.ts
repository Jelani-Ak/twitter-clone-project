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
  message = "";

  constructor(
    private registrationService: RegistrationService,
    private router: Router) {
  }

  ngOnInit(): void {
  }

  loginUser() {
    this.registrationService.logUserInFromRemote(this.user).subscribe(
      data => {
        console.log("Response received");
        this.router.navigateByUrl("/home");
      }
    );
  }

  goToSignUpPage() {
    this.router.navigateByUrl("/register")
  }
}

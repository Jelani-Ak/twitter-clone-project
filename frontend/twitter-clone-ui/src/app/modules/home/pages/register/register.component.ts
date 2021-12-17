import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../../../shared/models/user/user";
import {RegistrationService} from "../../../../core/services/registration/registration.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  user = new User();

  constructor(
    private router: Router,
    private registrationService: RegistrationService
  ) {
  }

  ngOnInit(): void {
  }

  registerUser() {
    this.registrationService.registerUserFromRemote(this.user).subscribe(
      data => {
        console.log("Registration Successful")
        setTimeout(() => {
          this.router.navigateByUrl("/login");
        }, 2000);
      }
    )
  }
}

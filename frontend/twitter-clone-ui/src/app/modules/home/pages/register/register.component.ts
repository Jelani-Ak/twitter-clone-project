import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../../../shared/models/user/user";
import {RegistrationService} from "../../../../core/services/registration/registration.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})

export class RegisterComponent implements OnInit {

  user = new User();

  constructor(
    private router: Router,
    private snackbar: MatSnackBar,
    private registrationService: RegistrationService
  ) {
  }

  ngOnInit(): void {
  }

  registerUser() {
    this.registrationService.registerUserFromRemote(this.user).subscribe(
      data => {
        console.log("Registration Successful")
        this.snackbar.open("Registration Successful", undefined, {duration: 2500})
        setTimeout(() => {
          this.router.navigateByUrl("/login");
        }, 2000);
      }
    )
  }
}

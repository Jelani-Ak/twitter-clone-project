import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../../../../core/services/registration/registration.service';
import { User } from '../../../../shared/models/user/user';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  user = new User();

  constructor(
    private router: Router,
    private snackbar: MatSnackBar,
    private registrationService: RegistrationService
  ) {}

  ngOnInit(): void {}

  loginUser() {
    this.registrationService.logUserInFromRemote(this.user).subscribe(() => {
      console.log(this.user);
      console.log('Login Successful');
      this.snackbar.open('Login Successful', undefined, { duration: 2500 });
      setTimeout(() => {
        this.router.navigateByUrl('/home');
      }, 2000);
    });
  }
}

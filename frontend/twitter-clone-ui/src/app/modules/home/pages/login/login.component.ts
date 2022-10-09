import { Component } from '@angular/core';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { User } from '../../../../shared/models/user';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  user = new User();

  constructor(
    private router: Router,
    private snackbar: MatSnackBar,
    private authenticationService: AuthenticationService
  ) {}

  loginUser() {
    this.authenticationService
    .logUserInFromRemote(this.user)
    .subscribe(() => {
      console.log('Login Successful');
      this.snackbar.open('Login Successful', undefined, { duration: 2500 });
      setTimeout(() => {
        this.router.navigateByUrl('/home');
      }, 2000);
    });
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../../shared/models/user/user';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user = new User();

  constructor(
    private router: Router,
    private snackbar: MatSnackBar,
    private authenticationService: AuthenticationService
  ) {}

  registerUser() {
    this.authenticationService
      .registerUserFromRemote(this.user)
      .subscribe(() => {
        console.log('Registration Successful');
        this.snackbar.open('Registration Successful', undefined, {
          duration: 2500,
        });
        setTimeout(() => {
          this.router.navigateByUrl('/login');
        }, 2000);
      });
  }
}

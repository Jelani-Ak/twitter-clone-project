import { Component } from '@angular/core';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { User } from '../../../../shared/models/user';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  user = new User();

  constructor(
    private router: Router,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  // TODO: Add next, complete and error
  public loginUser() {
    this.authenticationService
    .logUserInFromRemote(this.user)
    .subscribe(() => {
      console.log('Login Successful');
      this.snackbarService.displayToast('Login Successful', this.user.username);
      this.router.navigateByUrl('/home');
    });
  }
}

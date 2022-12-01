import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../../../../shared/models/user';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user: User = new User();

  constructor(
    private router: Router,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  public registerUser() {
    this.authenticationService.registerUserFromRemote(this.user).subscribe({
      complete: () => {
        console.log('Registration Successful');
        this.snackbarService.displayToast(
          'Registration Successful',
          this.user.username
        );
        this.router.navigateByUrl('/login');
      },
      error: (error) => {
        console.warn('Failed to register', error);
      },
    });
  }
}

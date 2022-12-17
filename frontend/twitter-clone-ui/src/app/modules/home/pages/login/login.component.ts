import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { User } from '../../../../shared/models/user';
import { Router } from '@angular/router';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { AbstractControlOptions, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from 'src/app/core/services/storage/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public loginForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
  } as AbstractControlOptions);

  public roles: string[] = [];
  public errorMessage: string = '';
  public loggedIn: boolean = false;
  public loginFailed: boolean = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private storageService: StorageService,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  public ngDoCheck(): void {
    if (this.storageService.isLoggedIn()) {
      this.loggedIn = true;
      this.roles = this.storageService.getUser().roles;
    }
  }

  public loginUser() {
    const formEmpty = 
      !this.loginForm.value.username || 
      !this.loginForm.value.password

    if (formEmpty) {
      return;
    }

    const username = this.loginForm.value.password;
    const password = this.loginForm.value.password;

    this.authenticationService
      .signInFromRemote({ username, password })
      .subscribe({
        next: (data) => {
          this.storageService.saveUser(data);
          this.loginFailed = false;
          this.loggedIn = true;
          this.roles = this.storageService.getUser().roles;
        },
        complete: () => {
          console.log('Login Successful');
          this.snackbarService.displayToast(
            'Login Successful',
            username
          );
          this.router.navigateByUrl('/home');
        },
        error: (error) => {
          console.warn('Failed to login, bad credentials', error);
          this.loginFailed = true;
        },
      });
  }
}

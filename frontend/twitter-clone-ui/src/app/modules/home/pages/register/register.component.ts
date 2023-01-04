import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../../../core/services/authentication/authentication.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import {
  AbstractControl,
  AbstractControlOptions,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { SignInRequestDTO } from 'src/app/shared/dto/sign-in-request.dto';
import { SignUpRequestDTO } from 'src/app/shared/dto/sign-up-request.dto';

export class CustomValidators {
  public static passwordMatcher(control: AbstractControl) {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
    const currentErrors = control.get('confirmPassword')?.errors;
    const confirmControl = control.get('confirmPassword');

    if (compare(password, confirmPassword)) {
      confirmControl?.setErrors({ ...currentErrors, notmatching: true });
    } else {
      confirmControl?.setErrors(currentErrors!);
    }
  }

  public static emailMatcher(control: AbstractControl) {
    const email = control.get('email')?.value;
    const confirmemail = control.get('confirmEmail')?.value;
    const currentErrors = control.get('confirmEmail')?.errors;
    const confirmControl = control.get('confirmEmail');

    if (compare(email, confirmemail)) {
      confirmControl?.setErrors({ ...currentErrors, notmatching: true });
    } else {
      confirmControl?.setErrors(currentErrors!);
    }
  }
}

function compare(firstString: string, secondString: string) {
  return firstString !== secondString && secondString !== '';
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  public registerForm: FormGroup = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]],
    confirmPassword: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    confirmEmail: ['', [Validators.required, , Validators.email]],
  }, {
    validators: [
      CustomValidators.passwordMatcher,
      CustomValidators.emailMatcher,
    ],
  } as AbstractControlOptions);

  public errorMessage: String = '';
  public isSuccessful: boolean = false;
  public signUpFailed: boolean = false;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private snackbarService: SnackbarService,
    private authenticationService: AuthenticationService
  ) {}

  public registerUser() {
    const formEmpty =
      !this.registerForm.value.username ||
      !this.registerForm.value.password ||
      !this.registerForm.value.email;

    if (formEmpty) {
      return;
    }

    const signUpRequest: SignUpRequestDTO = {
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
      email: this.registerForm.value.email,
    };

    this.authenticationService.signUpFromRemote(signUpRequest).subscribe({
      complete: () => {
        console.log('Registration Successful');
        this.snackbarService.displayToast(
          'Registration Successful',
          this.registerForm.value.username
        );
        this.isSuccessful = true;
        this.signUpFailed = false;
        this.router.navigateByUrl('/login');
      },
      error: (e) => {
        console.warn('Failed to register', e);
        this.errorMessage = e.error.errorMessage;
        this.signUpFailed = true;
      },
    });
  }
}

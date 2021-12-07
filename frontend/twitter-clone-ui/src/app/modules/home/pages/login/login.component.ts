import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;
  errorMessage = "Invalid Credentials";
  successMessage!: String;
  invalidLogin = false;
  loginSuccess = false;

  constructor() {
  }

  ngOnInit(): void {
  }

  handleLogin() {
    // this.authService.login().subscribe((result) => {
    //   this.invalidLogin = false;
    //   this.loginSuccess = true;
    //   this.successMessage = "Login Succesful";
    //   //Redirect to main page
    // }, () => {
    //   this.invalidLogin = true;
    //   this.loginSuccess = false;
    // });
  }

}

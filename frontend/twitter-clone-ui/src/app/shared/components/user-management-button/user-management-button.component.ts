import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-management-button',
  templateUrl: './user-management-button.component.html',
  styleUrls: ['./user-management-button.component.css'],
})
export class UserManagementButtonComponent {
  @Input() text!: string;

  constructor(private router: Router) {}

  navigate() {
    if (this.text === 'Logout') {
      console.warn(this.text + ' not implemented yet');
      return;
    }

    if (this.text === 'Admin') {
      this.router.navigateByUrl('/admin');
      return;
    }

    if (this.text === 'Twitter Clone') {
      this.router.navigateByUrl('/home');
      return;
    }

    this.router.navigateByUrl('/' + this.text.toLowerCase());
  }
}

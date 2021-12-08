import {Component, Input, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-management-button',
  templateUrl: './user-management-button.component.html',
  styleUrls: ['./user-management-button.component.css']
})
export class UserManagementButtonComponent implements OnInit {

  @Input() text!: string;

  constructor(private router: Router) {
  }

  ngOnInit(): void {
  }

  navigate() {
    this.router.navigateByUrl('/' + (this.text).toLowerCase())
  }

}

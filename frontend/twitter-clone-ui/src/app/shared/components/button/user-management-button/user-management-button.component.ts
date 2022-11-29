import { Component, Input, OnInit } from '@angular/core';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';

@Component({
  selector: 'app-user-management-button',
  templateUrl: './user-management-button.component.html',
  styleUrls: ['./user-management-button.component.css'],
})
export class UserManagementButtonComponent {
  @Input() text: string = "";

  constructor(private navigation: NavigationService) {}

  public navigate() {
    const location = this.text;
    switch (location) {
      case 'Logout':
        console.warn(`${location} not implemented yet`);
        break;
      case 'Twitter Clone':
        this.goToUrl('Home');
        break;
      default:
        this.goToUrl(location);
        break;
    }
  }

  private goToUrl(location: string) {
    this.navigation.navigate(location);
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navigation-panel',
  templateUrl: './navigation-panel.component.html',
  styleUrls: ['./navigation-panel.component.css'],
})
export class NavigationPanelComponent {
  constructor(public router: Router) {}

  shouldShowButton() {
    if (this.router.url !== '/home') {
      return true;
    }

    return false;
  }
}

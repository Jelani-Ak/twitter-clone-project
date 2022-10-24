import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'twitter-clone-ui';

  sidePanel = '300px';
  timeline = '600px';

  constructor(public router: Router) {}

  public showSidePanels(): boolean {
    if (
      this.router.url !== '/' &&
      this.router.url !== '/landing-page' &&
      this.router.url !== '/admin'
    ) {
      return true;
    }

    return false;
  }
}

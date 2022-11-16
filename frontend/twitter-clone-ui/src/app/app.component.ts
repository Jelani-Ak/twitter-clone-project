import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  TITLE = 'twitter-clone-ui';

  SIDE_PANEL = '300px';
  TIMELINE = '600px';
  ADMIN = '100%';

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

  public getWidth(): string {
    if (this.router.url === '/admin') {
      return this.ADMIN;
    }

    return this.TIMELINE;
  }
}

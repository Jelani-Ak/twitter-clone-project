import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  TITLE: string = 'twitter-clone-ui';

  TIMELINE_WIDTH: string = '600px';
  ADMIN_WIDTH: string = '100%';

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
      return this.ADMIN_WIDTH;
    }

    return this.TIMELINE_WIDTH;
  }
}

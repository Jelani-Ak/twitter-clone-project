import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  constructor(private router: Router) {}

  public navigate(url: String) {
    this.router.navigate([`/${url.toLowerCase()}`]).then(
      () => {
        console.log(`Navigated to ${url}`);
      },
      (error) => {
        console.log(`Failed to navigate to ${url}`, error);
      }
    );
  }
}

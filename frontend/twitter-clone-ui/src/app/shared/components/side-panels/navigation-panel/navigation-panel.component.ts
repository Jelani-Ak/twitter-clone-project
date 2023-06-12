import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from 'src/app/core/services/utility/utility.service';

@Component({
  selector: 'app-navigation-panel',
  templateUrl: './navigation-panel.component.html',
  styleUrls: ['./navigation-panel.component.css'],
})
export class NavigationPanelComponent {
  public get isWithinView(): boolean {
    return this.utilityService.isWithinView;
  }

  constructor(public router: Router, public utilityService: UtilityService) {}

  public shouldShowButton() {
    return this.router.url !== '/home' ? true : false;
  }
}

import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';

@Component({
  selector: 'app-navigation-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.css'],
})
export class NavigationButtonComponent {
  @Input() text!: string;
  @Input() icon!: string;

  constructor(
    public dialog: MatDialog,
    private navigation: NavigationService
  ) {}

  navigate() {
    const location = this.text;
    switch (location) {      
      case 'Explore':
      case 'Notifications':
      case 'Messages': //Direct messages
      case 'Bookmarks': //Tweets that have been saved
      case 'Lists': //Focused tweets based on preference
      case 'Profile': //Allow the user to change details/settings
      case 'More': //Open combobox with extra features
        console.warn(`${location} not implemented yet`);
        break;
      case 'Tweet':
        this.openDialog();
        break;
      default:
        this.goToUrl(location);
        break;
    }
  }

  goToUrl(location: string) {
    this.navigation.navigate(location);
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.width = '700' + 'px';

    this.dialog.open(DialogBoxComponent, dialogConfig);
  }
}

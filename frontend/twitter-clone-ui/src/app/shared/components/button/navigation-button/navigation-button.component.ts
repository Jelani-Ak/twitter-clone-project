import { Component, Input } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';
import { ComposeTweetComponent } from '../../dialog/compose-tweet/compose-tweet.component';

@Component({
  selector: 'app-navigation-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.css'],
})
export class NavigationButtonComponent {
  @Input() text!: string;
  @Input() icon!: string;

  dialogOpen: boolean = false;

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
        this.openComposeTweetDialog();
        break;
      default:
        this.goToUrl(location);
        break;
    }
  }

  goToUrl(location: string) {
    this.navigation.navigate(location);
  }

  openComposeTweetDialog() {
    this.dialogOpen = true;

    this.dialog.open(ComposeTweetComponent, {
      id: 'compose-tweet',
      data: {
        dialogOpen: this.dialogOpen
      },
      autoFocus: true,
      width: '700px',
    });
  }
}

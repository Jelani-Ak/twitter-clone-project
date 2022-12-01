import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';
import { TweetType } from 'src/app/shared/models/tweet';
import { ComposeTweetComponent } from '../../dialog/compose-tweet/compose-tweet.component';

@Component({
  selector: 'app-navigation-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.css'],
})
export class NavigationButtonComponent {
  @Input() public text: string = "";
  @Input() public icon: string = "";

  dialogOpen: boolean = false;

  constructor(
    private dialog: MatDialog,
    private navigation: NavigationService
  ) {}

  public navigate() {
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

  public goToUrl(location: string) {
    this.navigation.navigate(location);
  }

  private openComposeTweetDialog() {
    this.dialog.open(ComposeTweetComponent, {
      id: 'compose-tweet',
      data: {
        tweetType: TweetType.TWEET,
        textAreaLabel: "Compose Tweet",        
        placeholder: "What's happening",
        dialogOpen: this.dialogOpen = true
      },
      autoFocus: true,
      width: '700px',
    });
  }
}

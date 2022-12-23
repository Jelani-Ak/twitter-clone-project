import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/core/services/storage/storage.service';
import { TweetType } from 'src/app/shared/models/tweet';
import { ComposeTweetComponent } from '../../dialog/compose-tweet/compose-tweet.component';

@Component({
  selector: 'app-navigation-button',
  templateUrl: './navigation-button.component.html',
  styleUrls: ['./navigation-button.component.css'],
})
export class NavigationButtonComponent {
  @Input() public text: string = '';
  @Input() public icon: string = '';

  public get currentUser(): any {
    return this.storageService.getUser();
  }

  public dialogOpen: boolean = false;

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private storageService: StorageService
  ) {}

  public navigate() {
    const location = this.text.toLowerCase();
    switch (location) {
      case 'explore':
      case 'notifications':
      case 'messages': //Direct messages
      case 'bookmarks': //Tweets that have been saved
      case 'lists': //Focused tweets based on preference
        console.warn(`${location} not implemented yet`);
        break;
      case 'profile':
        const userId = this.currentUser.id;
        const username = this.currentUser.username;
        this.goToProfile(userId, username);
        break;
      case 'more': //Open combobox with extra features
        console.warn(`${location} not implemented yet`);
        break;
      case 'tweet':
        this.openComposeTweetDialog();
        break;
      default:
        this.router.navigate([`${location}`]);
        break;
    }
  }

  public goToProfile(userId: string, username: string) {
    this.router.navigate([`profile/${username}`], {
      queryParams: { userId: userId },
    });
  }

  private openComposeTweetDialog() {
    this.dialog.open(ComposeTweetComponent, {
      id: 'compose-tweet',
      data: {
        tweetType: TweetType.TWEET,
        textAreaLabel: 'Compose Tweet',
        placeholder: "What's happening",
        dialogOpen: (this.dialogOpen = true),
      },
      autoFocus: true,
      width: '700px',
    });
  }
}

import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from '../../models/tweet/tweet';

@Component({
  selector: 'app-tweet-management-buttons',
  templateUrl: './tweet-management-buttons.component.html',
  styleUrls: ['./tweet-management-buttons.component.css'],
})
export class TweetManagementButtonsComponent {
  @Input() public tweet!: Tweet;

  constructor(
    private snackbar: MatSnackBar,
    private tweetService: TweetService
  ) {}

  deleteTweet() {
    this.tweetService
      .deleteTweetFromRemote(this.tweet!.tweetId)
      .subscribe(() => {
        this.snackbar.open('Tweet Deleted', undefined, {
          duration: 2500,
        });
      });
  }

  addComment() {
    // TODO: Write logic to create a comment
    console.log('Add comment not implemented yet');
  }
}

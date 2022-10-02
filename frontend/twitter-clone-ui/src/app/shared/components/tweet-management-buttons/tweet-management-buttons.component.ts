import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from '../../models/tweet/tweet';

@Component({
  selector: 'app-tweet-management-buttons',
  templateUrl: './tweet-management-buttons.component.html',
  styleUrls: ['./tweet-management-buttons.component.css'],
})
export class TweetManagementButtonsComponent implements OnInit {
  @Input() public tweet: Tweet | undefined;

  constructor(
    private snackbar: MatSnackBar,
    private tweetService: TweetService
  ) {}

  ngOnInit(): void {}

  deleteTweet() {
    this.tweetService
    .deleteTweet(this.tweet?.tweetId)
    .subscribe((data) => {
      console.log(data)
      console.log(this.tweet);
      console.log('Tweet Deleted');
      this.snackbar.open('Tweet Deleted', undefined, {
        duration: 2500,
      });
    });
  }
}

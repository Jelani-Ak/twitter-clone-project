import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { Tweet } from './tweet';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent {
  // TODO: Delete later
  placeholderImage =
    'https://about.twitter.com/content/dam/about-twitter/en/brand-toolkit/brand-download-img-1.jpg.twimg.1920.jpg';

  constructor(
    public tweetService: TweetService,
    private snackbar: MatSnackBar
  ) {}

  deleteTweet(tweet: Tweet) {
    this.tweetService.tweets = this.tweetService.tweets.filter(
      (tweetIndex) => tweetIndex != tweet
    );

    this.tweetService.deleteTweetFromRemote(tweet.tweetId).subscribe(() => {
      this.snackbar.open('Tweet Deleted', 'Ok', {
        duration: 2500,
      });
    });
  }
}

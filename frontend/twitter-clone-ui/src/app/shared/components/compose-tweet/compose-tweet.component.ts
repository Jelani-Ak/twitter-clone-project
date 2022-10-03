import { Component } from '@angular/core';
import { Tweet } from '../../models/tweet/tweet';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent {
  tweet = new Tweet();

  fileSelectForm;

  content!: string;
  fileSelected!: boolean;

  constructor(
    private snackbar: MatSnackBar,
    public tweetService: TweetService
  ) {
    this.fileSelectForm = new FormGroup({
      firstName: new FormControl(),
    });
  }

  createTweet() {
    this.tweetService.createTweetFromRemote(this.tweet).subscribe((data) => {
      this.tweetService.tweets.push(data);
      this.snackbar.open('Tweet Created Successfully', 'Ok', {
        duration: 2500,
      });
    });
  }
}

import { Component } from '@angular/core';
import { Tweet } from '../../models/tweet/tweet';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileUploadService } from 'src/app/core/services/file-upload/file-upload.service';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent {
  tweet = new Tweet();

  content!: string;

  constructor(
    private snackbar: MatSnackBar,
    public tweetService: TweetService,
    public fileUploadService: FileUploadService
  ) {}

  createTweet() {
    this.tweetService.createTweetFromRemote(this.tweet).subscribe((data) => {
      this.tweetService.tweets.push(data);
      this.snackbar.open('Tweet Created Successfully', 'Ok', {
        duration: 2500,
      });
    });
  }

  upload(event: Event) {
    this.fileUploadService.upload(event);
  }

  cancel(input: any) {
    input.value = null;
  }
}

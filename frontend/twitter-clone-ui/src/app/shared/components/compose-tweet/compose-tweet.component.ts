import { Component } from '@angular/core';
import { Tweet } from '../../models/tweet';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent {
  tweet = new Tweet();

  content!: string;

  selectedFile: File | null = null;

  constructor(
    private snackbar: MatSnackBar,
    public tweetService: TweetService,
    public mediaService: MediaService
  ) {}

  createTweet() {
    if (this.selectedFile != null) {
      this.mediaService
        .uploadMediaFromRemote(this.selectedFile)
        .subscribe((media) => {
          this.mediaService.getMediaById(media.mediaId).subscribe((media) => {
            this.tweet.media = media;

            this.createTweetFromRemote();
          });
        });

      return;
    }

    this.createTweetFromRemote();
  }

  private createTweetFromRemote() {
    this.tweetService.createTweetFromRemote(this.tweet).subscribe((tweet) => {
      this.tweetService.tweets.push(tweet);
      console.log(tweet);
    });

    this.snackbar.open('Tweet Created Successfully', 'Ok', {
      duration: 2500,
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = <File> event.target.files[0];
  }

  cancel(input: any, form: NgForm) {
    input.value = null;
    this.selectedFile = null;
    form.reset();
  }
}

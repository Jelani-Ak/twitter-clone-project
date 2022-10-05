import { Component } from '@angular/core';
import { Tweet } from '../../models/tweet/tweet';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { Media } from '../../models/media/media';

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
        .subscribe((data) => {
          this.mediaService.getMedia(data.mediaId).subscribe((media) => {
            this.tweet.media = media;
            console.log(this.tweet);
          });
        });
    }

    this.tweetService.createTweetFromRemote(this.tweet).subscribe((data) => {
      console.log(data);
      this.tweetService.tweets.push(data);
      this.snackbar.open('Tweet Created Successfully', 'Ok', {
        duration: 2500,
      });
    });
  }

  onFileSelected(event: any) {
    console.log(event);
    this.selectedFile = <File>event.target.files[0];
  }

  cancel(input: any) {
    input.value = null;
    this.selectedFile = null;
  }
}

import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { Tweet } from '../../models/tweet';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent {
  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  // TODO: Delete later
  placeholderImage =
    'https://about.twitter.com/content/dam/about-twitter/en/brand-toolkit/brand-download-img-1.jpg.twimg.1920.jpg';

  constructor(
    public tweetService: TweetService,
    private snackbar: MatSnackBar,
    private mediaService: MediaService
  ) {}

  addComment(tweet: Tweet) {
    console.log(tweet);
    console.log("Comment not implemented yet");
  }

  deleteTweet(tweet: Tweet) {
    this.tweetService.tweets = this.tweetService.tweets.filter(
      (tweetIndex) => tweetIndex != tweet
    );

    if (tweet.media) {
      this.deleteTweetAndMediaFromRemote(
        tweet.tweetId,
        tweet.media.mediaId,
        tweet.media.mediaKey
      );
      return;
    }

    this.deleteTweetFromRemote(tweet.tweetId);
  }

  isAcceptableImage(tweetMediaType: string | undefined) {
    if (tweetMediaType == 'image/png' || 'image/jpg') {
      this.imageLoaded = true;
      return true;
    }

    return false;
  }

  isAcceptableVideo(tweetMediaType: string | undefined) {
    if (tweetMediaType == 'video/webm') {
      this.videoLoaded = true;
      return true;
    }

    return false;
  }

  private deleteTweetFromRemote(tweetId: string) {
    this.tweetService.deleteTweetFromRemote(tweetId).subscribe();

    this.snackbar.open('Tweet Deleted Successfully', 'Ok', {
      duration: 2500,
    });
  }

  private deleteTweetAndMediaFromRemote(
    tweetId: string,
    mediaId: string,
    mediaKey: string
  ) {
    this.deleteTweetFromRemote(tweetId);
    this.mediaService.deleteMediaFromRemote(mediaId).subscribe();
    this.mediaService.deleteS3Media(mediaKey).subscribe();
  }
}

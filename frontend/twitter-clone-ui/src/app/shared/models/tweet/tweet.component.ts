import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
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
    private snackbar: MatSnackBar,
    private mediaService: MediaService
  ) {}

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

  isImage(tweetMediaType: string | undefined) {
    if (tweetMediaType == null) {
      return false;
    }

    if (tweetMediaType == 'image/png' || 'image/jpg') {
      return true;
    }

    return false;
  }

  isVideo(tweetMediaType: string | undefined) {
    if (tweetMediaType == null) {
      return false;
    }

    if (tweetMediaType == 'video/webm') {
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

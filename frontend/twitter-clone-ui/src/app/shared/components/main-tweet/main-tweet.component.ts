import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from '../../models/tweet';

@Component({
  selector: 'app-main-tweet',
  templateUrl: './main-tweet.component.html',
  styleUrls: ['./main-tweet.component.css'],
})
export class MainTweetComponent {
  @Input() tweet: Tweet = new Tweet();

  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  // TODO: Replace with actual profile images
  placeholderImage = 'assets/Images/bird.jpg';

  constructor(
    public tweetService: TweetService,
    private snackbar: MatSnackBar,
    private mediaService: MediaService
  ) {}

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

  addComment() {
    console.log('Comment not implemented yet');
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

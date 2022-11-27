import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from '../../../models/tweet';
import { ComposeCommentComponent } from '../../dialog/compose-comment/compose-comment.component';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent {
  @Input() tweet!: Tweet;

  dialogOpen = false;

  imageLoaded: boolean = false;
  videoLoaded: boolean = false;

  // TODO: Replace with actual profile images
  placeholderImage = 'assets/Images/bird.jpg';

  constructor(
    public tweetService: TweetService,
    private dialog: MatDialog,
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
    this.openComposeCommentDialog();
  }

  deleteTweet(tweet: Tweet) {
    this.tweetService.tweets = this.tweetService.tweets.filter(
      (tweetIndex) => tweetIndex.tweetId !== tweet.tweetId
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

  private openComposeCommentDialog() {
    this.dialogOpen = true;

    this.dialog.open(ComposeCommentComponent, {
      id: 'compose-comment',
      data: { 
        tweet: this.tweet, 
        dialogOpen: this.dialogOpen
      },
      autoFocus: true,
      width: '700px',
    });
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
    this.mediaService.deleteCloudinaryMedia(mediaKey).subscribe();
  }
}

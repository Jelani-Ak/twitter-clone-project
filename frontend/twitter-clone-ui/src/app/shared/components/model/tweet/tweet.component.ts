import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MediaService } from 'src/app/core/services/media/media.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { TweetDTO, TweetService, } from 'src/app/core/services/tweet/tweet.service';
import { ComposeCommentComponent } from '../../dialog/compose-comment/compose-comment.component';
import { Tweet } from '../../../models/tweet';
import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent {
  @Input() public tweet: Tweet = new Tweet();

  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;
  private dialogOpen: boolean = false;

  constructor(
    public tweetService: TweetService,
    private dialog: MatDialog,
    private mediaService: MediaService,
    private snackbarService: SnackbarService
  ) {}

  public isAcceptableImage(tweetMediaType: string | undefined) {
    if (tweetMediaType == 'image/png' || 'image/jpg') {
      this.imageLoaded = true;
      return true;
    }

    return false;
  }

  public isAcceptableVideo(tweetMediaType: string | undefined) {
    if (tweetMediaType == 'video/webm') {
      this.videoLoaded = true;
      return true;
    }

    return false;
  }

  // TODO: Add confirmation dialog for deleting tweet

  public addComment() {
    this.openComposeCommentDialog();
  }

  private deleteTweet(tweet: Tweet): void {
    const tweetData = this.tweetService.buildTweetDTO(tweet);
    this.deleteTweetFromCache(tweet);

    if (!tweet.media) {
      console.log(`Deleting Tweet..`);
    } else {
      console.log(`Deleting Tweet and Media..`);
    }

    this.snackbarService.displayToast('Tweet Deleted Successfully', 'Ok');
    this.deleteTweetFromRemote(tweetData);
  }

  private deleteTweetFromCache(tweet: Tweet): void {
    this.tweetService.tweets = this.tweetService.tweets.filter(
      (tweetIndex) => tweetIndex.tweetId !== tweet.tweetId
    );
  }

  private deleteTweetFromRemote(tweetData: TweetDTO): void {
    const tweetHasMedia: boolean =
      tweetData.mediaData.mediaId != undefined &&
      tweetData.mediaData.mediaKey != undefined;

    this.tweetService.deleteTweetFromRemote(tweetData.tweetId).subscribe({
      complete: () => {
        console.log(`Tweet deleted succesfully`);
      },
      error: (error) => {
        console.error(`Failed to delete tweet`, error);
      },
    });
    if (tweetHasMedia) {
      this.mediaService
        .deleteMediaFromRemote(tweetData.mediaData.mediaId)
        .subscribe({
          complete: () => {
            console.log(`Media deleted succesfully from Repository`);
          },
          error: (error) => {
            console.error(`Failed to delete media from repository`, error);
          },
        });
      this.mediaService
        .deleteCloudinaryMedia(tweetData.mediaData.mediaKey)
        .subscribe({
          complete: () => {
            console.log(`Media deleted succesfully from Cloudinary`);
          },
          error: (error) => {
            console.error(`Failed to delete media from Cloudinary`, error);
          },
        });
    }
  }

  private openComposeCommentDialog() {
    const dialogRef = this.dialog.open(ComposeCommentComponent, {
      id: 'compose-comment',
      data: {
        tweet: this.tweet,
        dialogOpen: this.dialogOpen = true,
      },
      autoFocus: true,
      width: '700px',
    });

    dialogRef.afterClosed().subscribe((data) => {
      if (data) {
        this.tweet.comments.push(data);
        this.tweet.commentCount += 1;
      }
      this.dialogOpen = false;
    });
  }

  public openConfirmationDialog(tweet: Tweet) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      id: 'delete-tweet',
      data: {
        type: "Tweet",
        dialogOpen: this.dialogOpen = true,
      },
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((data: any) => {
      if (data == "yes") { this.deleteTweet(tweet) }
      this.dialogOpen = false;
    });
  }
}

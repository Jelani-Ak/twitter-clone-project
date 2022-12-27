import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from '../../../models/tweet';
import { TweetAndMediaDTO } from '../../../models/tweet';
import { TweetDTO } from '../../../models/tweet';
import { TweetType } from '../../../models/tweet';
import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';
import { ComposeTweetComponent } from '../../dialog/compose-tweet/compose-tweet.component';
import { Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user/user.service';
import { User } from 'src/app/shared/models/user';
import { StorageService } from 'src/app/core/services/storage/storage.service';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent implements OnInit, OnChanges {
  @Input() public tweet: Tweet = new Tweet();

  public get currentUser(): any {
    return this.storageService.getUser();
  }

  public tweetAuthor: User = new User();

  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;
  private dialogOpen: boolean = false;
  private likedTweet: boolean = false;

  constructor(
    public tweetService: TweetService,
    private router: Router,
    private dialog: MatDialog,
    private userService: UserService,
    private storageService: StorageService,
    private snackbarService: SnackbarService
  ) {}

  public ngOnChanges(): void {
    const tweetLoaded = Object.keys(this.tweet).length > 0;
    if (tweetLoaded) {
      this.initialiseTweetAuthor();
    }
  }

  public ngOnInit(): void {
    this.checkIfTweetIsLiked();
  }

  private checkIfTweetIsLiked() {
    this.userService.findByUserId(this.currentUser.id).subscribe({
      next: (user) => {
        const matchingTweet = [...user.likedTweets].find(
          (tweet) => tweet.tweetId == this.tweet.tweetId
        );
        if (matchingTweet) {
          this.likedTweet = true;
        }
      },
    });
  }

  private initialiseTweetAuthor() {
    this.userService.findByUserId(this.tweet.userId).subscribe({
      next: (user) => {
        this.tweetAuthor = user;
      },
      complete: () => {
        console.log('Tweet author data loaded');
      },
      error: (error) => {
        console.error('Failed to retrieve tweet author data', error);
      },
    });
  }

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

  public addComment() {
    this.openComposeDialog();
  }

  public likeTweet() {
    const tweetData: TweetDTO = {
      tweetId: this.tweet.tweetId,
      userId: this.currentUser.id,
    };

    this.tweetService.likeTweet(tweetData).subscribe({
      next: () => {
        this.likedTweet = !this.likedTweet;
        if (this.likedTweet == true) {
          this.tweet.likeCount++;
        } else {
          this.tweet.likeCount--;
        }
      },
      complete: () => {
        console.log('Sucessfully liked tweet');
      },
      error: (error) => {
        console.error('Failed to like tweet', error);
      },
    });
  }

  private deleteTweet(tweet: Tweet): void {
    const tweetData = this.tweetService.buildTweetDTO(tweet);
    this.deleteTweetFromCache(tweet);

    const hasNoMedia = !tweet.media;
    if (hasNoMedia) {
      console.log(`Deleting Tweet..`);
    } else {
      console.log(`Deleting Tweet and Media..`);
    }

    this.snackbarService.displayToast('Tweet Deleted Successfully');
    this.deleteTweetFromRemote(tweetData);
  }

  private deleteTweetFromCache(tweet: Tweet): void {
    this.tweetService.tweets = this.tweetService.tweets.filter(
      (tweetIndex) => tweetIndex.tweetId !== tweet.tweetId
    );
  }

  private deleteTweetFromRemote(tweetData: TweetAndMediaDTO): void {
    this.tweetService.deleteTweetFromRemote(tweetData.tweetDTO).subscribe({
      complete: () => {
        console.log(`Tweet deleted succesfully`);
      },
      error: (error) => {
        console.error(`Failed to delete tweet`, error);
      },
    });
  }

  private openComposeDialog() {
    const dialogRef = this.dialog.open(ComposeTweetComponent, {
      id: 'compose-comment',
      data: {
        tweet: this.tweet,
        tweetType: TweetType.COMMENT,
        textAreaLabel: 'Compose Comment',
        placeholder: 'Tweet your reply',
        dialogOpen: (this.dialogOpen = true),
      },
      autoFocus: true,
      width: '700px',
    });

    dialogRef.afterClosed().subscribe((data) => {
      if (data) {
        this.tweet.comments.push(data);
        this.tweet.commentCount++;
      }
      this.dialogOpen = false;
    });
  }

  public openConfirmationDialog(tweet: Tweet) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      id: 'delete-tweet',
      data: {
        title: `Delete ${TweetType.TWEET}`,
        message: `Are you sure you want to delete ${TweetType.TWEET}`,
        dialogOpen: (this.dialogOpen = true),
      },
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((data: string) => {
      if (data == 'yes') {
        this.deleteTweet(tweet);
        this.router.navigate(['/home']);
      }
      this.dialogOpen = false;
    });
  }

  public viewTweet() {
    this.router.navigate([`/tweet/${this.tweet.tweetId}`], {
      queryParams: { tweetId: this.tweet.tweetId },
    });
  }

  public goToProfile() {
    this.router.navigate([`/profile/${this.tweetAuthor.username}`], {
      queryParams: { userId: this.tweetAuthor.userId },
    });
  }

  public showViewTweet() {
    const viewingTweet: boolean = this.router.url.split('/')[1] == 'tweet';
    if (viewingTweet) {
      return false;
    }

    return true;
  }
}

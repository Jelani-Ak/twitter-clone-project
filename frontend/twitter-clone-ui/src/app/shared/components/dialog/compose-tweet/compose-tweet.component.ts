import { Component, Inject, OnInit } from '@angular/core';
import {
  Comment,
  CreateTweetDTO,
  Tweet,
  TweetType,
} from 'src/app/shared/models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { StorageService } from 'src/app/core/services/storage/storage.service';
import { User } from 'src/app/shared/models/user';
import { UserService } from 'src/app/core/services/user/user.service';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent implements OnInit {
  public get currentUser(): any {
    return this.storageService.getUser();
  }

  public tweet: any = new Object();
  public tweetAuthor: User = new User();

  private selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private userService: UserService,
    private tweetService: TweetService,
    private storageService: StorageService,
    private activatedRoute: ActivatedRoute,
    private snackbarService: SnackbarService,
    private dialogRef: MatDialogRef<ComposeTweetComponent>
  ) {}

  public ngOnInit(): void {
    this.initialiseTweetData();
    this.initialiseTweetAuthor();
    this.initializeParentTweetId();
  }

  // Prepare tweet and comment for creation
  private initialiseTweetData() {
    const comment: boolean = this.data.tweetType == TweetType.COMMENT;
    const commentHomepage: boolean = this.router.url == '/home' && comment;
    if (comment || commentHomepage) {
      this.tweet = new Comment();
      this.tweet.userId = this.currentUser.id;
      this.tweet.tweetType = TweetType.COMMENT;
      return;
    }

    const tweet: boolean = this.data.tweetType == TweetType.TWEET;
    const tweetHomepage: boolean = this.router.url == '/home';
    if (tweet || tweetHomepage) {
      this.tweet = new Tweet();
      this.tweet.userId = this.currentUser.id;
      this.tweet.tweetType = TweetType.TWEET;
      return;
    }
  }

  // Prepare the HTML template information
  private initialiseTweetAuthor() {
    const isTweet = this.data?.tweetType == TweetType.TWEET;
    const prepareComment = this.data?.tweet?.userId;
    const prepareTweet = this.tweet.userId;

    if (prepareComment == undefined || prepareTweet == undefined) {
      return;
    }

    const userId = isTweet ? prepareTweet : prepareComment;

    this.userService.findByUserId(userId).subscribe({
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

  // Additional preparation for comment creation
  private initializeParentTweetId() {
    const notAComment: boolean = this.data.tweetType != TweetType.COMMENT;
    if (notAComment) {
      return;
    }

    const onHomepage: boolean = this.router.url == '/home';
    const onProfile: boolean = this.router.url.split('/')[1] == 'profile';
    const creatingComment: boolean = Object.keys(this.data).length > 0;
    if ((onHomepage || onProfile) && creatingComment) {
      this.tweet.parentTweetId = this.data.tweet.tweetId;
      return;
    }

    const viewingTweet: boolean = this.router.url.split('/')[1] == 'tweet';
    if (viewingTweet) {
      this.activatedRoute.queryParams.subscribe({
        next: (queryParams: Params) => {
          this.tweet.parentTweetId = queryParams['tweetId'];
        },
        complete: () => {
          console.log('Initialised parent Tweet ID');
        },
        error: () => {
          console.error('Failed to set parent tweet ID');
        },
      });
    }
  }

  public create(input: any) {
    try {
      const hasNoMedia = this.selectedFile == null;
      if (hasNoMedia) {
        console.log(`Creating ${this.tweet.tweetType}..`);
      } else {
        console.log(`Creating ${this.tweet.tweetType} with Media..`);
      }

      const data: CreateTweetDTO = {
        tweet: this.tweet,
        file: this.selectedFile,
      };

      this.createTweet(data);

      this.snackbarService.displayToast(
        `${this.tweet.tweetType} Created Successfully`
      );
    } catch (error) {
      console.error(`Failed to create ${this.tweet.tweetType}`, error);
    }

    this.cancel(input, true);
  }

  public onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }

  public cancel(input: any, clearText: boolean) {
    if (clearText) this.tweet.content = null;
    input.value = null;
    this.selectedFile = null;
    console.log('Selected file removed');
  }

  public disableSubmitButton() {
    const noContent = !this.tweet.content;
    const noFile = !this.selectedFile;

    if (noContent && noFile) {
      return true;
    }

    return false;
  }

  public dialogIsOpen(): string {
    return this.data.dialogOpen ? '20px' : '0';
  }

  private createTweet(data: CreateTweetDTO) {
    const tweet = this.data.tweetType == TweetType.TWEET;
    if (tweet) {
      this.createTweetFromRemote(data);
      return;
    }

    const comment = this.data.tweetType == TweetType.COMMENT;
    if (comment) {
      this.createCommentFromRemote(data);
      return;
    }

    const onHomepage = this.router.url == '/home';
    const dialogIsNotOpen = this.dialogIsNotOpen();
    if (onHomepage && dialogIsNotOpen) {
      this.createTweetFromRemote(data);
      return;
    }
  }

  private dialogIsNotOpen() {
    const dialogNotOpen =
      Object.keys(this.data).length === 0 &&
      Object.getPrototypeOf(this.data) === Object.prototype;

    return dialogNotOpen;
  }

  private createTweetFromRemote(data: CreateTweetDTO) {
    this.tweetService.createTweetFromRemote(data).subscribe({
      next: (tweet) => {
        this.tweetService.tweets.push(tweet);
      },
      complete: () => {
        console.log(`Tweet created succesfully`);
      },
      error: (error) => {
        console.warn(`Failed to create tweet`, error);
      },
    });
  }

  private createCommentFromRemote(data: CreateTweetDTO) {
    this.tweetService.createCommentFromRemote(data).subscribe({
      next: (comment) => {
        this.closeDialog(comment);
      },
      complete: () => {
        console.log(`Comment created sucessfully`);
      },
      error: (error) => {
        console.error('Faled to create comment', error);
      },
    });
  }

  private closeDialog(comment: Comment | null) {
    if (this.data.dialogOpen) {
      this.dialogRef.close(comment);
    }
  }
}

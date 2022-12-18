import { Component, Inject, OnInit } from '@angular/core';
import { Comment, Tweet, TweetType } from 'src/app/shared/models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MediaService } from 'src/app/core/services/media/media.service';
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
    private mediaService: MediaService,
    private storageService: StorageService,
    private activatedRoute: ActivatedRoute,
    private snackbarService: SnackbarService,
    private dialogRef: MatDialogRef<ComposeTweetComponent>
  ) {}

  public ngDoCheck(): void {} // Needed for ngOnInit?

  public ngOnInit(): void {
    this.initialiseTweetData();
    this.initialiseTweetAuthor();
    this.initializeParentTweetId();
  }

  // Prepare tweet and comment for creation
  private initialiseTweetData() {
    const tweet: boolean = this.data.tweetType == TweetType.TWEET;
    const onHomepage: boolean = this.router.url == '/home';
    if (tweet || onHomepage) {
      this.tweet = new Tweet();
      this.tweet.userId = this.currentUser.id;
      this.tweet.tweetType = TweetType.TWEET;
      return;
    }

    const comment: boolean = this.data.tweetType == TweetType.COMMENT;
    if (comment) {
      this.tweet = new Comment();
      this.tweet.userId = this.currentUser.id;
      this.tweet.tweetType = TweetType.COMMENT;
      return;
    }
  }

  // Additional preparation for comment creation
  private initializeParentTweetId() {
    const notAComment = !this.data && this.data.tweetType != TweetType.COMMENT;
    if (notAComment) {
      return;
    }

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

  // Prepare the template information
  private initialiseTweetAuthor() {
    const tweetType = this.data?.tweetType == TweetType.TWEET;
    const comment = this.data?.tweet?.userId;
    const tweet = this.tweet.userId;

    if (comment == undefined || tweet == undefined) {
      return;
    }

    const userId = tweetType ? tweet : comment;

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

  public create() {
    try {
      console.log(`Creating ${this.tweet.tweetType}..`);

      this.createTweet();
      this.createComment();
      this.createTweetFromHomepage();

      this.snackbarService.displayToast(
        `${this.tweet.tweetType} Created Successfully`,
        'Ok'
      );
    } catch (error) {
      console.error(`Failed to create ${this.tweet.tweetType}`, error);
    }
  }

  public onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }

  public cancel(input: any) {
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

  private createTweet() {
    const tweet =
      this.selectedFile == null && this.data.tweetType == TweetType.TWEET;
    const tweetWithMedia =
      this.selectedFile && this.data.tweetType == TweetType.TWEET;

    if (tweet) {
      this.createTweetFromRemote();
      return;
    }

    if (tweetWithMedia) {
      this.createTweetFromRemoteWithMedia();
      return;
    }
  }

  private createComment() {
    const comment =
      this.selectedFile == null && this.data.tweetType == TweetType.COMMENT;
    const commentWithMedia =
      this.selectedFile && this.data.tweetType == TweetType.COMMENT;

    if (comment) {
      this.createCommentFromRemote();
      return;
    }

    if (commentWithMedia) {
      this.createCommentFromRemoteWithMedia();
      return;
    }
  }

  private createTweetFromHomepage() {
    const tweet = this.selectedFile == null;
    const tweetWithMedia = this.selectedFile != null;
    const onHomepage = this.router.url == '/home';
    const dialogNotOpen =
      Object.keys(this.data).length === 0 &&
      Object.getPrototypeOf(this.data) === Object.prototype;

    if (tweet && onHomepage && dialogNotOpen) {
      this.createTweetFromRemote();
      return;
    }

    if (tweetWithMedia && onHomepage && dialogNotOpen) {
      this.createTweetFromRemoteWithMedia();
      return;
    }
  }

  // TODO: Commenting from the homepage does not work.
  // Only make it possible when vieweing the tweet?

  private createTweetFromRemote() {
    this.tweetService.createTweetFromRemote(this.tweet).subscribe({
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

  private createTweetFromRemoteWithMedia() {
    this.mediaService.uploadMediaFromRemote(this.selectedFile!).subscribe({
      next: (media) => {
        this.tweet!.media = media;
        this.createTweetFromRemote();
      },
      complete: () => {
        console.log(`Media uploaded sucessfully to Cloudinary`);
      },
      error: (error) => {
        console.error(`Failed to upload media to Cloudinary`, error);
      },
    });
  }

  private createCommentFromRemote() {
    this.tweetService.createCommentFromRemote(this.tweet).subscribe({
      next: (comment) => {
        const noMedia: boolean = !this.tweet!.media;
        if (noMedia) this.closeDialog(comment);
      },
      complete: () => {
        console.log(`Comment created sucessfully`);
      },
      error: (error) => {
        console.error('Faled to create comment', error);
      },
    });
  }

  private createCommentFromRemoteWithMedia() {
    this.mediaService.uploadMediaFromRemote(this.selectedFile!).subscribe({
      next: (media) => {
        this.tweet.media = media;
        this.tweetService.createCommentFromRemote(this.tweet).subscribe({
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
      },
      complete: () => {
        console.log(`Media uploaded sucessfully to Cloudinary`);
      },
      error: (error) => {
        console.error(`Failed to upload media to Cloudinary`, error);
      },
    });
  }

  private closeDialog(comment: Comment | null) {
    if (this.data.dialogOpen) {
      this.dialogRef.close(comment);
    }
  }
}

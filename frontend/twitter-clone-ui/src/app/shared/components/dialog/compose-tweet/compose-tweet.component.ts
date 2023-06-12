import { Component, Inject, OnInit } from '@angular/core';
import { Comment } from 'src/app/shared/models/tweet';
import { CreateTweetDTO } from 'src/app/shared/models/tweet';
import { Tweet } from 'src/app/shared/models/tweet';
import { TweetType } from 'src/app/shared/models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { Router } from '@angular/router';
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

  public tweet: Tweet | Comment = new Tweet();
  public tweetAuthor: User = new User();

  private selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private userService: UserService,
    private tweetService: TweetService,
    private storageService: StorageService,
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

  // Prepare the HTML template reply information
  private initialiseTweetAuthor() {
    const creatingAComment =
      this.data.tweetType == TweetType.COMMENT && this.data.dialogOpen;
    if (creatingAComment) {
      this.userService.findByUserId(this.data.tweet.userId).subscribe({
        next: (user) => {
          this.tweetAuthor = user;
        },
        complete: () => {
          console.log(`${this.tweet.tweetType} author data loaded`);
        },
        error: (error) => {
          console.error(
            `Failed to retrieve ${this.tweet.tweetType} author data`,
            error
          );
        },
      });
    }
  }

  // Additional preparation for comment creation
  private initializeParentTweetId() {
    if (this.tweet instanceof Comment) {
      this.tweet.parentTweetId = this.data.tweet.tweetId;
    }
  }

  public create(input: any) {
    try {
      const data: CreateTweetDTO = {
        tweet: this.tweet,
        file: this.selectedFile,
      };

      this.createTweet(data);

      this.snackbarService.displayToast(
        `${this.tweet.tweetType} Created Successfully`
      );
      this.cancel(input, true);
    } catch (error) {
      console.error(`Failed to create ${this.tweet.tweetType}`, error);
    }
  }

  public onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }

  public cancel(input: any, clearText: boolean) {
    if (clearText) this.tweet.content = undefined;
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
    this.selectedFile == null
      ? console.log(`Creating ${this.tweet.tweetType}..`)
      : console.log(`Creating ${this.tweet.tweetType} with Media..`);

    if (this.tweet instanceof Tweet) {
      this.createTweetFromRemote(data);
      return;
    }

    if (this.tweet instanceof Comment) {
      this.createCommentFromRemote(data);
      return;
    }
  }

  private createTweetFromRemote(data: CreateTweetDTO) {
    this.tweetService.createTweetFromRemote(data).subscribe({
      next: (tweet) => {
        this.tweetService.tweets.push(tweet);
      },
      complete: () => {
        console.log(`Tweet created succesfully`);
        this.closeDialog();
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

  private closeDialog(comment: Comment | null = null) {
    if (this.data.dialogOpen) {
      this.dialogRef.close(comment);
    }
  }
}

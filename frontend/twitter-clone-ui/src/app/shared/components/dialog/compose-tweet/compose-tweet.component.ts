import { Component, Inject, OnInit } from '@angular/core';
import { Comment, Tweet, TweetType } from 'src/app/shared/models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { ActivatedRoute, Params, Router } from '@angular/router';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent implements OnInit {
  public tweet: any = new Object();

  private selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private activatedRoute: ActivatedRoute,
    private snackbarService: SnackbarService,
    private dialogRef: MatDialogRef<ComposeTweetComponent>
  ) {}

  public ngOnInit(): void {
    this.initialiseData();
  }

  private initialiseData() {
    const tweet: boolean = this.data.tweetType == TweetType.TWEET;
    if (tweet) {
      this.tweet = new Tweet();
      this.tweet.tweetType = this.data.tweetType;
    }

    const comment: boolean = this.data.tweetType == TweetType.COMMENT;
    if (comment) {
      this.tweet = new Comment();
      this.initializeParentTweetId();
      this.tweet.tweetType = this.data.tweetType;
    }

    const onHomepage: boolean = this.router.url == '/home';
    if (onHomepage) {
      this.tweet = new Tweet();
      this.tweet.tweetType = TweetType.TWEET;
      return;
    }
  }

  public create() {
    try {
      console.log(`Creating ${this.tweet.tweetType}..`);

      this.createTweet();
      this.createComment();
      this.createTweetHomepage();

      this.snackbarService.displayToast(
        `${this.tweet.tweetType} Created Successfully`,
        'Ok'
      );
    } catch (error) {
      console.error('Failed to create tweet', error);
    } finally {
      this.closeDialog();
    }
  }

  public onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }

  // TODO: Does not correctly clear forms
  public cancel(input: any, form: NgForm) {
    input.value = null;
    this.selectedFile = null;
    form.reset();
    console.warn("Needs fixing. Doesn't properly remove files");
  }

  private createTweet() {
    const tweet =
      this.selectedFile == null && this.data.tweetType == TweetType.TWEET;
    const tweetWithMedia =
      this.selectedFile && this.data.tweetType == TweetType.TWEET;
    if (tweet) {
      this.createTweetFromRemote();
    } else if (tweetWithMedia) {
      this.createTweetFromRemoteWithMedia();
    }
  }

  private createComment() {
    const comment =
      this.selectedFile == null && this.data.tweetType == TweetType.COMMENT;
    const commentWithMedia =
      this.selectedFile && this.data.tweetType == TweetType.COMMENT;
    if (comment) {
      this.createCommentFromRemote();
    } else if (commentWithMedia) {
      this.createCommentFromRemoteWithMedia();
    }
  }

  private createTweetHomepage() {
    const tweet = this.selectedFile == null;
    const tweetWithMedia = this.selectedFile != null;
    if (tweet) {
      this.createTweetFromRemote();
    } else if (tweetWithMedia) {
      this.createTweetFromRemoteWithMedia();
    }
  }

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
        if (!this.tweet!.media) this.closeDialog(comment);
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

  private initializeParentTweetId() {
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

  private closeDialog(comment: Comment | null = null) {
    if (this.data.dialogOpen) {
      this.dialogRef.close(comment);
    }
  }
}

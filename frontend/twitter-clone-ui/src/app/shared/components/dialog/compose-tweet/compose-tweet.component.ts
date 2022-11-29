import { Component, Inject } from '@angular/core';
import { Tweet } from '../../../models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent {
  tweet: Tweet = new Tweet();
  content: String = new String();

  selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private snackbarService: SnackbarService,
    private dialogRef: MatDialogRef<ComposeTweetComponent>
  ) {}

  public createTweet() {
    try {
      console.log(`Creating tweet..`);

      if (this.selectedFile == null) {
        this.createTweetFromRemote();
      } else {
        this.createTweetFromRemoteWithMedia();
      }
      
      this.snackbarService.displayToast('Tweet Created Successfully', 'Ok');

    } finally {
      this.closeTweetDialog();
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
        this.tweet.media = media;
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

  private closeTweetDialog() {
    if (this.data.dialogOpen) {
      this.dialogRef.close();
    }
  }
}

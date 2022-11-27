import { Component, Inject } from '@angular/core';
import { Tweet } from '../../../models/tweet';
import { TweetService } from '../../../../core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css'],
})
export class ComposeTweetComponent {
  tweet = new Tweet();

  content!: string;
  selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackbar: MatSnackBar,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private dialogRef: MatDialogRef<ComposeTweetComponent>
  ) {}

  public createTweet() {
    try {
      if (this.selectedFile == null) {
        this.createTweetFromRemote();
      } else {
        this.createTweetFromRemoteWithMedia();
      }
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

  private createTweetFromRemote() {
    this.tweetService.createTweetFromRemote(this.tweet).subscribe((tweet) => {
      this.tweetService.tweets.push(tweet);
    });

    this.snackbar.open('Tweet Created Successfully', 'Ok', {
      duration: 2500,
    });
  }

  private createTweetFromRemoteWithMedia() {
    this.mediaService
      .uploadMediaFromRemote(this.selectedFile!)
      .subscribe((media) => {
        this.mediaService.getMediaById(media.mediaId).subscribe((media) => {
          this.tweet.media = media;

          this.createTweetFromRemote();
        });
      });
  }

  private closeDialog() {
    if (this.data.dialogOpen) {
      this.dialogRef.close();
    }
  }
}

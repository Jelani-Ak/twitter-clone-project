import { Component, Inject } from '@angular/core';
import { Comment } from '../../../models/comment';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-compose-comment',
  templateUrl: './compose-comment.component.html',
  styleUrls: ['./compose-comment.component.css'],
})
export class ComposeCommentComponent {
  comment = new Comment();

  content!: string;
  selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private snackbar: MatSnackBar,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private activatedRoute: ActivatedRoute,
    private dialogRef: MatDialogRef<ComposeCommentComponent>
  ) {}

  public createComment() {
    try {
      if (this.selectedFile == null) {
        this.createCommentFromRemote();
      } else {
        this.createCommentFromRemoteWithMedia();
      }
    } finally {
      this.closeDialog();
    }
  }

  public onFileSelected(event: any) {
    this.selectedFile = <File> event.target.files[0];
  }

  // TODO: Does not correctly clear forms
  public cancel(input: any, form: NgForm) {
    input.value = null;
    this.selectedFile = null;
    form.reset();
    console.warn("Needs fixing. Doesn't properly remove files");
  }

  private createCommentFromRemote() {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.comment.parentTweetId = queryParams['tweetId'];
    });

    this.tweetService
      .createCommentFromRemote(this.comment)
      .subscribe((comment) => {
        const index = this.tweetService.tweets.map((tweet) => {
          return tweet.tweetId;
        }).indexOf(this.comment.parentTweetId);

        this.tweetService.tweets[index].comments.push(comment);
      });

    this.snackbar.open('Tweet Created Successfully', 'Ok', {
      duration: 2500,
    });
  }

  private createCommentFromRemoteWithMedia() {
    this.mediaService
        .uploadMediaFromRemote(this.selectedFile!)
        .subscribe((media) => {
          this.mediaService.getMediaById(media.mediaId).subscribe((media) => {
            this.comment.media = media;

            this.createCommentFromRemote();
          });
        });
  }

  private closeDialog() {
    if (this.data.dialogOpen) {
      this.dialogRef.close();
    }
  }
}

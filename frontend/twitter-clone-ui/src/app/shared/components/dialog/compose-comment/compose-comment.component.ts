import { Component, Inject, OnInit } from '@angular/core';
import { Comment } from '../../../models/comment';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { MediaService } from 'src/app/core/services/media/media.service';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';

@Component({
  selector: 'app-compose-comment',
  templateUrl: './compose-comment.component.html',
  styleUrls: ['./compose-comment.component.css'],
})
export class ComposeCommentComponent implements OnInit {
  comment: Comment = new Comment();
  content: String = new String();

  selectedFile: File | null = null;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private activatedRoute: ActivatedRoute,
    private snackbarService: SnackbarService,
    private dialogRef: MatDialogRef<ComposeCommentComponent>
  ) {}

  public ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.comment.parentTweetId = queryParams['tweetId'];
    });
  }

  public createComment() {
    console.log('Creating comment..');

    if (this.selectedFile == null) {
      this.createCommentFromRemote();
    } else {
      this.createCommentFromRemoteWithMedia();
    }

    this.snackbarService.displayToast('Tweet Created Successfully', 'Ok');
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

  private createCommentFromRemote() {
    this.tweetService.createCommentFromRemote(this.comment).subscribe({
      next: (comment) => {
        if (!this.comment.media) {
          this.closeCommentDialog(comment);
        }
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
        this.comment.media = media;

        this.tweetService.createCommentFromRemote(this.comment).subscribe({
          next: (comment) => {
            this.closeCommentDialog(comment);
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

  private closeCommentDialog(comment: Comment) {
    if (this.data.dialogOpen) {
      this.dialogRef.close(comment);
    }
  }
}

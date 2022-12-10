import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MediaService } from 'src/app/core/services/media/media.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import {
  CommentDTO,
  TweetService,
} from 'src/app/core/services/tweet/tweet.service';
import { Comment, TweetType } from 'src/app/shared/models/tweet';
import { ConfirmationDialogComponent } from '../../dialog/confirmation-dialog/confirmation-dialog.component';

export type CommentIndex = {
  comment: Comment;
  index: number;
};

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent {
  @Input() public comment: Comment = new Comment();
  @Output() public deleteCommentEmit = new EventEmitter<CommentIndex>();
  @Output('decrement-comment') public decrementCommentEmit = new EventEmitter<number>();

  public dialogOpen: boolean = false;
  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  constructor(
    private dialog: MatDialog,
    private tweetService: TweetService,
    private mediaService: MediaService,
    private snackbarService: SnackbarService
  ) {}

  public isAcceptableImage(commentMediaType: string | undefined) {
    if (commentMediaType == 'image/png' || 'image/jpg') {
      this.imageLoaded = true;
      return true;
    }

    return false;
  }

  public isAcceptableVideo(commentMediaType: string | undefined) {
    if (commentMediaType == 'video/webm') {
      this.videoLoaded = true;
      return true;
    }

    return false;
  }

  private deleteComment(comment: Comment) {
    const commentData = this.tweetService.buildCommentDTO(comment);
    this.deleteCommentFromCache(comment);

    if (!comment.media) {
      console.log(`Deleting Comment..`);
    } else {
      console.log(`Deleting Comment and Media..`);
    }

    this.deleteCommentAndMediaFromRemote(commentData);
    this.snackbarService.displayToast('Comment Deleted Successfully', 'Ok');
  }

  private deleteCommentFromCache(comment: Comment) {
    this.tweetService.getTweetById(comment.parentTweetId).subscribe((tweet) => {
      const comments = tweet.comments;

      const commentIndex: number = comments
        .map((comment) => {
          return comment.commentId;
        })
        .indexOf(comment.commentId);

      const data: CommentIndex = { comment: comment, index: commentIndex };
      this.deleteCommentEmit.emit(data);
    });

    var event = new CustomEvent('decrement-comment-count', { detail: 1 });
    document.dispatchEvent(event);
  }

  private deleteCommentAndMediaFromRemote(commentData: CommentDTO) {
    const commentHasMedia: boolean =
      commentData.mediaData.mediaId != undefined &&
      commentData.mediaData.mediaKey != undefined;

    this.tweetService
      .deleteCommentFromRemote(commentData.commentDeleteDTO)
      .subscribe({
        complete: () => {
          console.log(`Comment deleted succesfully`);
        },
        error: (error) => {
          console.error(`Failed to delete Comment`, error);
        },
      });
    if (commentHasMedia) {
      this.mediaService
        .deleteMediaFromRemote(commentData.mediaData.mediaId)
        .subscribe({
          complete: () => {
            console.log(`Media deleted succesfully from Repository`);
          },
          error: (error) => {
            console.error(`Failed to delete media from repository`, error);
          },
        });
      this.mediaService
        .deleteCloudinaryMedia(commentData.mediaData.mediaKey)
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

  public openConfirmationDialog(comment: Comment) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      id: 'delete-comment',
      data: {
        type: TweetType.COMMENT,
        dialogOpen: (this.dialogOpen = true),
      },
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((data: any) => {
      if (data == 'yes') {
        this.deleteComment(comment);
      }
      this.dialogOpen = false;
    });
  }
}

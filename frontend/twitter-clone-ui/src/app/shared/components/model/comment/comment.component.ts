import { Component } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Input } from '@angular/core';
import { OnChanges } from '@angular/core';
import { Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { UserService } from 'src/app/core/services/user/user.service';
import { Comment } from 'src/app/shared/models/tweet';
import { CommentAndMediaDTO } from 'src/app/shared/models/tweet';
import { TweetType } from 'src/app/shared/models/tweet';
import { User } from 'src/app/shared/models/user';
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
export class CommentComponent implements OnChanges {
  @Input('comment') public comment: Comment = new Comment();
  @Output('delete-comment') public deleteCommentEmit = new EventEmitter<CommentIndex>();
  @Output('decrement-comment-count') public decrementCommentEmit = new EventEmitter<number>();

  public commentAuthor: User = new User();

  public dialogOpen: boolean = false;
  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  constructor(
    private dialog: MatDialog,
    private userService: UserService,
    private tweetService: TweetService,
    private snackbarService: SnackbarService
  ) {}

  public ngOnChanges(): void {
    const commentLoaded = Object.keys(this.comment).length > 0;
    if (commentLoaded) {
      const commentAuthorLoaded = Object.keys(this.commentAuthor).length > 0;
      if (commentAuthorLoaded) {
        return;
      }

      this.initialiseCommentAuthor();
    }
  }

  private initialiseCommentAuthor() {
    this.userService.findByUserId(this.comment.userId).subscribe({
      next: (user) => {
        this.commentAuthor = user;
      },
      complete: () => {
        console.log('Comment author data loaded');
      },
      error: (error) => {
        console.error('Failed to retrieve comment author data', error);
      },
    });
  }

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

    const hasNoMedia = !comment.media;
    if (hasNoMedia) {
      console.log(`Deleting Comment..`);
    } else {
      console.log(`Deleting Comment and Media..`);
    }

    this.deleteCommentAndMediaFromRemote(commentData);
    this.snackbarService.displayToast('Comment Deleted Successfully');
  }

  private deleteCommentFromCache(comment: Comment) {
    this.tweetService.getTweetById(comment.parentTweetId).subscribe((tweet) => {
      const comments = tweet.comments;

      const commentIndex: number = comments
        .map((comment) => {
          return comment.commentId;
        })
        .indexOf(comment.commentId);

      const data: CommentIndex = {
        comment: comment,
        index: commentIndex,
      };
      this.deleteCommentEmit.emit(data);
    });

    var event = new CustomEvent('decrement-comment-count', { detail: 1 });
    document.dispatchEvent(event);
  }

  private deleteCommentAndMediaFromRemote(data: CommentAndMediaDTO) {
    this.tweetService.deleteCommentFromRemote(data.commentDTO).subscribe({
      complete: () => {
        console.log(`Comment deleted succesfully`);
      },
      error: (error) => {
        console.error(`Failed to delete Comment`, error);
      },
    });
  }

  public openConfirmationDialog(comment: Comment) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
      id: 'delete-comment',
      data: {
        title: `Delete ${TweetType.COMMENT}`,
        message: `Are you sure you want to delete ${TweetType.COMMENT}`,
        dialogOpen: (this.dialogOpen = true),
      },
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((data: string) => {
      if (data == 'yes') {
        this.deleteComment(comment);
      }
      this.dialogOpen = false;
    });
  }
}

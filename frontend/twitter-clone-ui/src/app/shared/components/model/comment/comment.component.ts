import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MediaService } from 'src/app/core/services/media/media.service';
import { SnackbarService } from 'src/app/core/services/snackbar/snackbar.service';
import { CommentData, TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Comment } from 'src/app/shared/models/comment';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent {
  @Input() public comment: Comment = new Comment();
  @Output() public deleteCommentEmit = new EventEmitter<any>();

  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  constructor(
    private tweetService: TweetService,
    private mediaService: MediaService,
    private snackbarService: SnackbarService
  ) {}

  // TODO: Add confirmation dialog for deleting comment

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

  public deleteComment(comment: Comment) {
    const commentData = this.buildCommentData(comment);
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

      const index = comments.map((comment) => {
        return comment.commentId;
      }).indexOf(comment.commentId); 

      const data = { comment, index };
      this.deleteCommentEmit.emit(data);
    })
  }

  private deleteCommentAndMediaFromRemote(commentData: CommentData) {
    const commentHasMedia =
      commentData.mediaData.mediaId != undefined &&
      commentData.mediaData.mediaKey != undefined;

    this.tweetService
      .deleteCommentFromRemote(commentData.tweetAndCommentId)
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

  private buildCommentData(comment: Comment): CommentData {
    const commentData: CommentData = {
      tweetAndCommentId: {
        parentTweetId: comment.parentTweetId,
        commentId: comment.commentId,
      },
      mediaData: {
        mediaId: comment.media?.mediaId,
        mediaKey: comment.media?.mediaKey,
      },
    };

    return commentData;
  }
}

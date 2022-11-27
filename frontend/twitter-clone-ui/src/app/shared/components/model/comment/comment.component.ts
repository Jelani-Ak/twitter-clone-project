import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MediaService } from 'src/app/core/services/media/media.service';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Comment } from '../../../models/comment';

export type TweetAndCommentId = {
  parentTweetId: string;
  commentId: string;
};

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent {
  @Input() comments!: Comment[];

  public imageLoaded: boolean = false;
  public videoLoaded: boolean = false;

  constructor(
    private snackbar: MatSnackBar,
    public tweetService: TweetService,
    private mediaService: MediaService,
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

  public deleteComment(comment: Comment) {
    this.comments = this.comments.filter(
      (commentIndex) => commentIndex.commentId !== comment.commentId
    )

    const data: TweetAndCommentId = {
      parentTweetId: comment.parentTweetId,
      commentId: comment.commentId,
    };

    if (comment.media) {
      this.deleteCommentAndMediaFromRemote(
        data,
        comment.media.mediaId,
        comment.media.mediaKey
      );

      return;
    }

    this.deleteCommentFromRemote(data);
  }

  private deleteCommentFromRemote(data: TweetAndCommentId) {
    this.tweetService.deleteCommentFromRemote(data).subscribe();

    this.snackbar.open('Tweet Deleted Successfully', 'Ok', {
      duration: 2500,
    });
  }

  private deleteCommentAndMediaFromRemote(
    data: TweetAndCommentId,
    mediaId: string,
    mediaKey: string
  ) {
    this.deleteCommentFromRemote(data);
    this.mediaService.deleteMediaFromRemote(mediaId).subscribe();
    this.mediaService.deleteCloudinaryMedia(mediaKey).subscribe();
  }
}

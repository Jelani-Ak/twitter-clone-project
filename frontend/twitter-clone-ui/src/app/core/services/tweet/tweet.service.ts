import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tweet, Comment } from 'src/app/shared/models/tweet';
import { MediaData } from '../media/media.service';

export type TweetId = {
  tweetId: string;
};

export type TweetDTO = {
  tweetDeleteDTO: TweetDeleteDTO;
  mediaData: MediaData;
};

export type CommentDTO = {
  commentDeleteDTO: CommentDeleteDTO;
  mediaData: MediaData;
};

export type CommentDeleteDTO = {
  parentTweetId: string;
  commentId: string;
};

export type TweetDeleteDTO = {
  tweetId: string;
}

@Injectable({
  providedIn: 'root',
})
export class TweetService {
  public tweets: Tweet[] = [];

  private baseTweetUrl = 'http://localhost:8080/api/v1/tweet/';
  private baseCommentUrl = 'http://localhost:8080/api/v1/comment/';

  constructor(private http: HttpClient) {
    this.setup();
  }

  private setup(): void {
    this.cacheTweets();
  }

  private cacheTweets(): void {
    if (this.tweets.length > 0) {
      return;
    }

    this.getAllTweets().subscribe({
      next: (tweets) => {
        this.tweets = tweets;
      },
      complete: () => {
        console.log(`Loaded all tweets`);
      },
      error: (error) => {
        console.error(`Failed to load all tweets`, error);
      },
    });
  }

  public buildTweetDTO(tweet: Tweet): TweetDTO {
    const tweetDTO: TweetDTO = {
      tweetDeleteDTO: {
        tweetId: tweet.tweetId
      },
      mediaData: {
        mediaId: tweet.media?.mediaId,
        mediaKey: tweet.media?.mediaKey,
      },
    };

    return tweetDTO;
  }

  public buildCommentDTO(comment: Comment): CommentDTO {
    const commentDTO: CommentDTO = {
      commentDeleteDTO: {
        parentTweetId: comment.parentTweetId,
        commentId: comment.commentId,
      },
      mediaData: {
        mediaId: comment.media?.mediaId,
        mediaKey: comment.media?.mediaKey,
      },
    };

    return commentDTO;
  }

  // Tweets
  public createTweetFromRemote(tweet: any): Observable<Tweet> {
    return this.http.post<Tweet>(this.baseTweetUrl + 'create-tweet', tweet);
  }

  public deleteTweetFromRemote(tweetData: TweetDeleteDTO): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseTweetUrl + 'delete-tweet', {
      body: tweetData,
    });
  }

  public getTweetById(tweetId: string): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseTweetUrl + tweetId);
  }

  public getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseTweetUrl + 'get-all-tweets');
  }

  // Comments // TODO: Create and move into comment.service.ts?
  public createCommentFromRemote(comment: any): Observable<Comment> {
    return this.http.post<Comment>(this.baseCommentUrl + 'create-comment', comment);
  }

  public deleteCommentFromRemote(commentData: CommentDeleteDTO): Observable<Comment> {
    return this.http.delete<Comment>(this.baseCommentUrl + 'delete-comment', {
      body: commentData,
    });
  }
}

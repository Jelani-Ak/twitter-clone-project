import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tweet, Comment } from 'src/app/shared/models/tweet';
import { MediaData } from '../media/media.service';

export type TweetAndCommentId = {
  parentTweetId: string;
  commentId: string;
};

export type TweetId = {
  tweetId: string;
};

export type TweetDTO = {
  tweetId: string;
  mediaData: MediaData;
};

export type CommentDTO = {
  tweetAndCommentId: TweetAndCommentId;
  mediaData: MediaData;
};

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
      tweetId: tweet.tweetId,
      mediaData: {
        mediaId: tweet.media?.mediaId,
        mediaKey: tweet.media?.mediaKey,
      },
    };

    return tweetDTO;
  }

  public buildCommentDTO(comment: Comment): CommentDTO {
    const commentDTO: CommentDTO = {
      tweetAndCommentId: {
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
    return this.http.post<Tweet>(this.baseTweetUrl + 'create', tweet);
  }

  public deleteTweetFromRemote(tweetId: string | undefined): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseTweetUrl + 'delete/' + tweetId);
  }

  public getTweetById(tweetId: string): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseTweetUrl + tweetId);
  }

  public getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseTweetUrl + 'all');
  }

  // Comments // TODO: Create and move into comment.service.ts?
  public createCommentFromRemote(comment: any): Observable<Comment> {
    return this.http.post<Comment>(this.baseCommentUrl + 'create', comment);
  }

  public deleteCommentFromRemote(data: TweetAndCommentId): Observable<Comment> {
    return this.http.delete<Comment>(this.baseCommentUrl + 'delete', {
      body: data,
    });
  }
}

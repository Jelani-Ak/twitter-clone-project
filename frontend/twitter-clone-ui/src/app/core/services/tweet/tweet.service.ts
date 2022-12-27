import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Tweet,
  Comment,
  TweetAndMediaDTO,
  CommentAndMediaDTO,
  CommentDTO,
  CreateTweetDTO,
  TweetDTO,
} from 'src/app/shared/models/tweet';

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

  public buildTweetDTO(tweet: Tweet): TweetAndMediaDTO {
    const tweetDTO: TweetAndMediaDTO = {
      tweetDTO: {
        userId: tweet.userId,
        tweetId: tweet.tweetId,
      },
      mediaDTO: {
        mediaId: tweet.media?.mediaId,
        mediaKey: tweet.media?.mediaKey,
      },
    };

    return tweetDTO;
  }

  public buildCommentDTO(comment: Comment): CommentAndMediaDTO {
    const commentDTO: CommentAndMediaDTO = {
      commentDTO: {
        userId: comment.userId,
        commentId: comment.commentId,
        parentTweetId: comment.parentTweetId,
      },
      mediaDTO: {
        mediaId: comment.media?.mediaId,
        mediaKey: comment.media?.mediaKey,
      },
    };

    return commentDTO;
  }

  // Tweets
  public createTweetFromRemote(data: CreateTweetDTO): Observable<Tweet> {
    const formData = new FormData();
    formData.append('body', new Blob([JSON.stringify(data.tweet)]));

    const fileSelected = data.file != null;
    if (fileSelected) {
      formData.append('file', data.file!, data.file!.name);
    }

    return this.http.post<Tweet>(this.baseTweetUrl + 'create-tweet', formData);
  }

  public deleteTweetFromRemote(tweetData: TweetDTO): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseTweetUrl + 'delete-tweet', {
      body: tweetData,
    });
  }

  public getTweetById(tweetId: string): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseTweetUrl + tweetId + '/get-tweet');
  }

  public getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseTweetUrl + 'get-all-tweets');
  }

  public likeTweet(data: TweetDTO): Observable<Tweet> {
    return this.http.post<Tweet>(this.baseTweetUrl + 'like-tweet', data);
  }

  // Comments // TODO: Create and move into comment.service.ts?
  public createCommentFromRemote(data: CreateTweetDTO): Observable<Comment> {
    const formData = new FormData();
    formData.append('body', new Blob([JSON.stringify(data.tweet)]));

    const fileSelected = data.file != null;
    if (fileSelected) {
      formData.append('file', data.file!, data.file!.name);
    }

    return this.http.post<Comment>(this.baseCommentUrl + 'create-comment', formData);
  }

  public deleteCommentFromRemote(commentData: CommentDTO): Observable<Comment> {
    return this.http.delete<Comment>(this.baseCommentUrl + 'delete-comment', {
      body: commentData,
    });
  }
}

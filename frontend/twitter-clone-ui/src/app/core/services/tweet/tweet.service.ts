import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tweet } from 'src/app/shared/models/tweet';
import { Comment } from 'src/app/shared/models/comment';
import { TweetAndCommentId } from 'src/app/shared/components/model/comment/comment.component';

@Injectable({
  providedIn: 'root',
})
export class TweetService {
  public tweets: Tweet[] = [];
  public contentLoaded: boolean = false;

  private baseTweetUrl = 'http://localhost:8080/api/v1/tweet/';
  private baseCommentUrl = 'http://localhost:8080/api/v1/comment/';

  constructor(private http: HttpClient) {
    this.setup();
  }

  setup(): void {
    this.loadTweets();
  }

  loadTweets() {
    this.getAllTweets().subscribe((tweets) => {
      this.tweets = tweets;
    });

    this.contentLoaded = true;
  }

  // Tweets
  createTweetFromRemote(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>(this.baseTweetUrl + 'create', tweet);
  }

  deleteTweetFromRemote(tweetId: string): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseTweetUrl + 'delete/' + tweetId);
  }

  getTweetById(tweetId: string): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseTweetUrl + tweetId);
  }

  getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseTweetUrl + 'all');
  }

  // Comments // TODO: Move into comment.service.ts
  createCommentFromRemote(comment: Comment): Observable<Comment> {
    return this.http.post<Comment>(this.baseCommentUrl + 'create', comment);
  }

  deleteCommentFromRemote(data: TweetAndCommentId): Observable<Comment> {
    return this.http.delete<Comment>(this.baseCommentUrl + 'delete', { body: data });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tweet } from 'src/app/shared/models/tweet/tweet';

@Injectable({
  providedIn: 'root',
})
export class TweetService {
  public tweets: Tweet[] = [];

  private baseUrl = 'http://localhost:8080/api/v1/tweet/';

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
  }

  getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseUrl + 'all');
  }

  getTweet(tweetId: string): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseUrl + tweetId);
  }

  createTweetFromRemote(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>(this.baseUrl + 'create', tweet);
  }

  deleteTweetFromRemote(tweetId: string): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseUrl + 'delete/' + tweetId);
  }
}

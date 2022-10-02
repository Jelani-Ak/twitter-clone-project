import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tweet } from '../../../shared/models/tweet/tweet';

@Injectable({
  providedIn: 'root',
})
export class TweetService {
  private baseUrl = 'http://localhost:8080/api/v1/tweet/';

  constructor(private http: HttpClient) {}

  getAllTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseUrl + 'all');
  }

  getTweet(tweetId: number): Observable<Tweet> {
    return this.http.get<Tweet>(this.baseUrl + tweetId);
  }

  composeTweet(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>(this.baseUrl + 'create', tweet);
  }

  deleteTweet(tweetId: any): Observable<Tweet> {
    return this.http.delete<Tweet>(this.baseUrl + 'delete/' + tweetId);
  }
}

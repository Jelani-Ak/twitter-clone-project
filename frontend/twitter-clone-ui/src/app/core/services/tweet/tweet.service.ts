import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tweet} from "../../../shared/components/tweet/tweet";

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  private baseUrl = "http://localhost:8080/api/v1/tweet";

  constructor(private http: HttpClient) {
  }

  getTweets(): Observable<Tweet[]> {
    return this.http.get<Tweet[]>(this.baseUrl + '/all');
  }

  composeTweet(tweet: Tweet): Observable<Tweet> {
    return this.http.post<Tweet>((this.baseUrl + '/create'), tweet)
  }
}

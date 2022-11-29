import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TweetService } from '../../../../core/services/tweet/tweet.service';

@Component({
  selector: 'app-tweet-timeline',
  templateUrl: './tweet-timeline.component.html',
  styleUrls: ['./tweet-timeline.component.css'],
})
export class TweetTimelineComponent {
  constructor(
    private router: Router,
    public tweetService: TweetService
  ) {}

  public goToTweet(tweetId: string) {
    this.router.navigate([`/tweet/${tweetId}`], {
      queryParams: { tweetId: tweetId }
    });
  }
}

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TweetService } from '../../../core/services/tweet/tweet.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css'],
})
export class TimelineComponent {
  constructor(
    public tweetService: TweetService,
    private router: Router
  ) {}

  goToTweet(tweetId: string) {
    this.router.navigate([`/tweet/${tweetId}`], {
      queryParams: { tweetId: tweetId }
    });
  }
}

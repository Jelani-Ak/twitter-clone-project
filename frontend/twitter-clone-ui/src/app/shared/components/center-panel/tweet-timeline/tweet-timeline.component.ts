import { Component } from '@angular/core';
import { TweetService } from '../../../../core/services/tweet/tweet.service';

@Component({
  selector: 'app-tweet-timeline',
  templateUrl: './tweet-timeline.component.html',
  styleUrls: ['./tweet-timeline.component.css'],
})
export class TweetTimelineComponent {
  constructor(public tweetService: TweetService) {}
}

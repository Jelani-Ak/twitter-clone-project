import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { TweetService } from '../../../core/services/tweet/tweet.service';
import { Tweet } from './tweet';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent implements OnInit {
  tweets: Tweet[] = [];
  placeholderImage =
    'https://about.twitter.com/content/dam/about-twitter/en/brand-toolkit/brand-download-img-1.jpg.twimg.1920.jpg';

  constructor(private tweetService: TweetService) {}

  ngOnInit(): void {
    this.getTweets();
  }

  getTweets() {
    this.tweetService.getAllTweets().subscribe((tweets) => {
      this.tweets = tweets;
    });
  }
}

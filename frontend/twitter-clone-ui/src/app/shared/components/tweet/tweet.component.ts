import {Component, OnInit} from '@angular/core';
import {TweetService} from "../../../core/services/tweet/tweet.service";
import {Tweet} from "./tweet";

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent implements OnInit {
  tweets: Tweet[] = [];

  constructor(private tweetService: TweetService) {
  }

  ngOnInit(): void {
    this.tweetService.getTweets().subscribe((tweets: Tweet[]) => {
      console.log(tweets);
      this.tweets = tweets;
    });
  }
}

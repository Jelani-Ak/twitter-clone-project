import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {TweetService} from "../../../core/services/tweet/tweet.service";
import {Tweet} from "./tweet";

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css']
})
export class TweetComponent implements OnInit {
  tweets: Tweet[] = [];
  columnsToDisplay = [
    'userId',
    'username',
    'password'
  ];

  constructor(private tweetService: TweetService, private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {
    this.getTweets();
  }

  addTweet(tweet: Tweet) {
    this.tweetService.composeTweet(tweet).subscribe(() => this.tweets.push(tweet));
    this.cd.detectChanges()
  }

  getTweets() {
    this.tweetService.getAllTweets().subscribe((tweets) => {
      console.log(tweets);
      this.tweets = tweets;
    });
  }
}

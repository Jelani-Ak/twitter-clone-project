import {Component, OnInit} from '@angular/core';
import {Tweet} from "../../models/tweet/tweet";
import {TweetService} from "../../../core/services/tweet/tweet.service";

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css']
})
export class ComposeTweetComponent implements OnInit {

  content!: string;
  fileSelected!: boolean;

  constructor(private tweetService: TweetService) {
  }

  ngOnInit(): void {
  }

  addTweet() {
    let tweet: Tweet = {
      username: "",
      tweetUrl: "",
      content: this.content,
      commentCount: 0,
      retweetCount: 0,
      createdDate: "",
      likeCount: 0
    };
    this.tweetService.composeTweet(tweet).subscribe(
      (response: Tweet) => {
        console.log(response)
        console.log(tweet)
        console.log(this.content)
      })
  }
}

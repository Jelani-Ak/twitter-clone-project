import {Component, OnInit} from '@angular/core';
import {TweetService} from "../../services/tweet/tweet.service";
import {Tweet} from "../../../shared/components/models/tweet/tweet";

@Component({
  selector: 'app-dialog-create-tweet',
  templateUrl: './dialog-create-tweet.component.html',
  styleUrls: ['./dialog-create-tweet.component.css']
})
export class DialogCreateTweetComponent implements OnInit {

  content!: string;

  constructor(private tweetService: TweetService) {

  }

  ngOnInit(): void {
  }

  addTweet() {
    let tweet: Tweet = {
      username: "",
      url: "",
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

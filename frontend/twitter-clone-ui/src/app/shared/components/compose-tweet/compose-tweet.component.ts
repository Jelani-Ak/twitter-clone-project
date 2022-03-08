import {Component, OnInit} from '@angular/core';
import {Tweet} from "../../models/tweet/tweet";
import {TweetService} from "../../../core/services/tweet/tweet.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css']
})
export class ComposeTweetComponent implements OnInit {

  tweet = new Tweet();

  content!: string;
  fileSelected!: boolean;


  constructor(
    private snackbar: MatSnackBar,
    private tweetService: TweetService
  ) {
  }

  ngOnInit(): void {
  }

  addTweet() {
    this.tweetService.composeTweet(this.tweet).subscribe(
      data => {
        console.log(data)
        console.log(this.tweet)
        console.log(this.content)
        this.snackbar.open("Tweet Created Successfully", undefined, {duration: 2500})
      })
  }
}

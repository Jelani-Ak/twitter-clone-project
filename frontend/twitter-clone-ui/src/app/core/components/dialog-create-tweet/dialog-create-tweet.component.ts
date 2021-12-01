import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {TweetService} from "../../services/tweet/tweet.service";
import {Tweet} from "../../../shared/components/tweet/tweet";

@Component({
  selector: 'app-dialog-create-tweet',
  templateUrl: './dialog-create-tweet.component.html',
  styleUrls: ['./dialog-create-tweet.component.css']
})
export class DialogCreateTweetComponent implements OnInit {

  constructor(private tweetService: TweetService) {

  }

  ngOnInit(): void {
  }

  onAddTweet(addForm: NgForm): void {
    this.tweetService.composeTweet(addForm.value).subscribe(
      (response: Tweet) => {
        console.log(response)
        console.log(addForm.value)
      })
  }
}

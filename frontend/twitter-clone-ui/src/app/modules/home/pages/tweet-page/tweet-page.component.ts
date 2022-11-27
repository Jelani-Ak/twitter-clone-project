import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-tweet-page',
  templateUrl: './tweet-page.component.html',
  styleUrls: ['./tweet-page.component.css'],
})
export class TweetPageComponent implements OnInit {
  tweet = new Tweet();
  tweetId: string = "";

  options: string[] = ['Likes', 'Newest', 'Oldest'];

  selectedItem!: string;

  constructor(
    private location: Location,
    private tweetService: TweetService,
    private activatedRoute: ActivatedRoute
  ) {
    this.selectDefaultOption();
  }

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.tweetId = queryParams['tweetId'];
    })

    this.tweetService.getTweetById(this.tweetId).subscribe((tweet) => {
      this.tweet = tweet;
    });
  }

  goBack() {
    this.location.back();
  }

  // TODO: Replace with service to sort comments
  printSelection() {
    console.log(this.selectedItem);
  }

  private selectDefaultOption() {
    this.selectedItem = this.options[0];
  }
}

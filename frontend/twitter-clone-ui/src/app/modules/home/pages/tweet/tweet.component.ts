import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-tweet',
  templateUrl: './tweet.component.html',
  styleUrls: ['./tweet.component.css'],
})
export class TweetComponent implements OnInit {
  tweet = new Tweet();
  tweetId: string = "";

  constructor(
    private location: Location,
    private tweetService: TweetService,
    private activatedRoute: ActivatedRoute
  ) {}

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
}

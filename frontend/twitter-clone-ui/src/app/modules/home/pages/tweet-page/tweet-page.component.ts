import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Comment } from 'src/app/shared/models/comment';
import { Tweet } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-tweet-page',
  templateUrl: './tweet-page.component.html',
  styleUrls: ['./tweet-page.component.css'],
})
export class TweetPageComponent implements OnInit {
  tweet = new Tweet();
  comments: Comment[] = [];
  tweetId: string = '';

  options: string[] = ['Likes', 'Newest', 'Oldest'];

  selectedItem!: string;

  constructor(
    private location: Location,
    private tweetService: TweetService,
    private activatedRoute: ActivatedRoute
  ) {
    this.selectDefaultOption();
  }

  public ngOnInit(): void {
    this.loadTweet();
  }

  // TODO: Last page reference is lost on page refresh
  public goBack() {
    this.location.back();
  }

  // TODO: Implement comment sorting
  public printSelection() {
    console.warn('Sorting not implemented yet');
  }

  private selectDefaultOption() {
    this.selectedItem = this.options[0];
  }

  private loadTweet() {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.tweetService
        .getTweetById(queryParams['tweetId'])
        .subscribe((tweet) => {
          this.tweet = tweet;
          this.comments = tweet.comments;
        });
    });
  }
}

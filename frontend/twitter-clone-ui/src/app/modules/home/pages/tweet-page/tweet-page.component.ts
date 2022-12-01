import { Location } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet, Comment } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-tweet-page',
  templateUrl: './tweet-page.component.html',
  styleUrls: ['./tweet-page.component.css'],
})
export class TweetPageComponent {
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
    this.loadTweetAndComments();
  }

  @HostListener('document:decrement-comment-count', ['$event'])
  public decrementCommentCount(event: any) {
    this.tweet.commentCount -= event.detail;
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

  private loadTweetAndComments() {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.tweetService.getTweetById(queryParams['tweetId']).subscribe({
        next: (tweet) => {
          this.tweet = tweet;
          this.comments = tweet.comments;
        },
        complete: () => {
          console.log('Loaded tweet and comments');
        },
        error: (error) => {
          console.error('Failed to load tweet and comments', error);
        },
      });
    });
  }
}

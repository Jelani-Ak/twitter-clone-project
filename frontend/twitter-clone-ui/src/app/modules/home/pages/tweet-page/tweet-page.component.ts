import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { Tweet, Comment } from 'src/app/shared/models/tweet';

@Component({
  selector: 'app-tweet-page',
  templateUrl: './tweet-page.component.html',
  styleUrls: ['./tweet-page.component.css'],
})
export class TweetPageComponent implements OnInit {
  public tweet: Tweet = new Tweet();
  public comments: Comment[] = [];
  public options: string[] = ['Newest', 'Oldest', 'Likes'];

  public selectedOption: string = '';

  constructor(
    private router: Router,
    private tweetService: TweetService,
    private activatedRoute: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.selectDefaultOption();
    this.loadTweetAndComments();
  }

  public goHome() {
    this.router.navigate(['home']);
  }

  public sortComments() {
    const selectedOption = this.selectedOption;
    switch (selectedOption) {
      case 'Newest':
        this.sortCommentsByNewest();
        break;
      case 'Oldest':
        this.sortCommentsByOldest();
        break;
      case 'Likes':
        this.sortCommentsByLikes();
        break;
    }

    console.log(`Sorted by ${selectedOption}`);
  }

  public deleteComment($event: any) {
    this.comments.forEach((comment) => {
      if (comment == $event.comment) {
        this.comments.splice($event.index, 1);
        this.tweet.commentCount--;
      }
    });
  }

  private selectDefaultOption() {
    this.selectedOption = this.options[0];
  }

  private sortCommentsByLikes() {
    this.comments.sort((a, b) => {
      const likeCountA = a.likeCount;
      const likeCountB = b.likeCount;

      if (likeCountA > likeCountB) return 1;
      if (likeCountA < likeCountB) return -1;
      return 0;
    });
  }

  private sortCommentsByNewest() {
    this.comments.sort((a, b) => {
      const dateA = new Date(a.dateOfCreation);
      const dateB = new Date(b.dateOfCreation);

      return dateB.getTime() - dateA.getTime();
    });
  }

  private sortCommentsByOldest() {
    this.comments.sort((a, b) => {
      const dateA = new Date(a.dateOfCreation);
      const dateB = new Date(b.dateOfCreation);

      return dateA.getTime() - dateB.getTime();
    });
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
          this.sortComments();
        },
        error: (error) => {
          console.error('Failed to load tweet and comments', error);
        },
      });
    });
  }
}

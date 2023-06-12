import { HttpClient } from '@angular/common/http';
import { AfterViewInit } from '@angular/core';
import { Component } from '@angular/core';
import { ElementRef } from '@angular/core';
import { OnInit } from '@angular/core';
import { ViewChild } from '@angular/core';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';
import { UtilityService } from 'src/app/core/services/utility/utility.service';
import { Tweet } from 'src/app/shared/models/tweet';

export type Page = {
  content: Array<Tweet>;
};
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit, AfterViewInit {
  public loggedInUsersTweets: Tweet[] = [];
  public page: number = 0;
  public loading: boolean = false;

  private baseTweetUrl = 'http://localhost:8080/api/v1/tweet/';

  @ViewChild('scrollContainer', { static: true })
  scrollContainer: ElementRef<HTMLElement> = {} as ElementRef;

  @ViewChild('composeTweet', { static: false })
  composeTweet: ElementRef<HTMLElement> = {} as ElementRef;

  constructor(
    public tweetService: TweetService,
    private http: HttpClient,
    private utilityService: UtilityService
  ) {}

  public ngOnInit(): void {
    this.loadPageableTweets();
  }

  public ngAfterViewInit(): void {
    this.onScroll();
  }

  public loadPageableTweets() {
    this.loading = true;
    this.http
      .get<Page>(`${this.baseTweetUrl}get-pageable-tweet?page=${this.page}`)
      .subscribe({
        next: (page: Page) => {
          console.log('Loading next page..');
          this.loggedInUsersTweets.push(...page.content);
          this.page++;
          this.loading = false;
        },
        complete: () => {
          console.log(`Loaded page ${this.page} of tweets`);
        },
        error: (error) => {
          console.error('', error);
        },
      });
  }

  public onScroll() {
    if (!this.composeTweet.nativeElement) return;

    console.log('Made it here');

    const composeTweetRect =
      this.composeTweet.nativeElement.getBoundingClientRect();

    const composeTweetOutOfView =
      composeTweetRect.top < 0 || composeTweetRect.bottom > window.innerHeight;

    this.utilityService.setIsWithinView(composeTweetOutOfView);

    if (this.loading) return;

    const scrollContainer = this.scrollContainer?.nativeElement;
    const scrollTop = scrollContainer?.scrollTop;
    const clientHeight = scrollContainer?.clientHeight;
    const scrollHeight = scrollContainer?.scrollHeight;

    console.log('Scroll Top + Client Height = ' + (scrollTop + clientHeight));
    console.log('Scroll Height = ' + scrollHeight);

    if (scrollTop + clientHeight >= scrollHeight) {
      this.loadPageableTweets();
    }
  }
}

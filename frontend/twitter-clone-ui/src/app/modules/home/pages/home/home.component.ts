import { Component } from '@angular/core';
import { TweetService } from 'src/app/core/services/tweet/tweet.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  constructor(public tweetService: TweetService) {}
}

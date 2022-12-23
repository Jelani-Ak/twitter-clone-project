import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user/user.service';
import { Tweet } from 'src/app/shared/models/tweet';
import { User } from 'src/app/shared/models/user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  public user: User = new User();
  public tweetsAndRetweets: Set<Tweet> = new Set();

  public placeholderBackgroundImage = '/assets/images/cm.gif';
  public placeholderProfileImage = '/assets/images/me.png';

  constructor(
    private router: Router,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.initialiseUser();
  }

  public goHome() {
    this.router.navigate(['/home']);
  }

  private initialiseUser() {
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.userService.findByUserId(queryParams['userId']).subscribe({
        next: (user) => {
          this.user = user;
          this.tweetsAndRetweets = user.tweets;
        },
        complete: () => {
          console.log('Successfully retrieved user information');
        },
        error: (error) => {
          console.error('Failed to rerieve user infromation', error);
        },
      });
    });
  }
}

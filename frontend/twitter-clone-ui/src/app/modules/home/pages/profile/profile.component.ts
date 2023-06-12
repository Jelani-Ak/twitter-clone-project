import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { UserService } from 'src/app/core/services/user/user.service';
import { EditProfileComponent } from 'src/app/shared/components/dialog/edit-profile/edit-profile.component';
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
  public tweetsAndComments: Array<any> = new Array();
  public tweetsAndCommentsWithMedia: Array<any> = new Array();
  public likedTweetsAndComments: Set<Tweet> = new Set();

  public placeholderBackgroundImage = '/assets/images/cm.gif';
  public placeholderProfileImage = '/assets/images/me.png';

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private userService: UserService,
    private activatedRoute: ActivatedRoute
  ) {}

  public ngOnInit(): void {
    this.initialiseUser();
  }

  public goHome() {
    this.router.navigate(['/home']);
  }

  public openEditProfile() {
    this.dialog.open(EditProfileComponent, {
      id: 'edit-profile',
      width: '600px',
      height: 'auto',
    });
  }

  // TODO: Implement follow user function
  public followUser() {
    console.warn('Follow not implemented yet');
  }

  private initialiseUser() {
    const startTime = performance.now();
    this.activatedRoute.queryParams.subscribe((queryParams: Params) => {
      this.userService.findByUserId(queryParams['userId']).subscribe({
        next: (user) => {
          // User
          this.user = user;

          // Setup tweets and retweets
          this.tweetsAndRetweets = user.tweets;

          // Setup tweets and commments then sort by newest
          this.tweetsAndComments = [...user.tweets, ...user.comments].sort(
            (a: any, b: any) => {
              const dateA = new Date(a.dateOfCreation);
              const dateB = new Date(b.dateOfCreation);

              return dateA.getTime() - dateB.getTime();
            }
          );

          // Setup tweets and comments with media
          this.tweetsAndCommentsWithMedia = [
            ...user.tweets,
            ...user.comments,
          ].filter((tweetOrComment: any) => tweetOrComment.media);

          // TODO: Include comments
          // Setup liked tweets and comments
          this.likedTweetsAndComments = user.likedTweets;
        },
        complete: () => {
          const endTime = performance.now();
          const timeTaken = ((endTime - startTime) / 1_000).toFixed(6);
          console.log(`Retrieved profile information in ${timeTaken} seconds`);
        },
        error: (error) => {
          console.error('Failed to rerieve user infromation', error);
        },
      });
    });
  }
}

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AppRoutingModule } from './app-routing.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA, } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';

import { AppComponent } from './app.component';
import { HeaderComponent } from './core/components/header/header.component';
import { FooterComponent } from './core/components/footer/footer.component';
import { HomeComponent } from './modules/home/pages/home/home.component';
import { NavigationButtonComponent } from './shared/components/button/navigation-button/navigation-button.component';
import { NavigationPanelComponent } from './shared/components/side-panels/navigation-panel/navigation-panel.component';
import { SearchNewsPanelComponent } from './shared/components/side-panels/search-news-panel/search-news-panel.component';
import { UserComponent } from './shared/components/model/user/user.component';
import { TweetTimelineComponent } from './shared/components/center-panel/tweet-timeline/tweet-timeline.component';
import { ComposeTweetComponent } from './shared/components/dialog/compose-tweet/compose-tweet.component';
import { UserManagementButtonComponent } from './shared/components/button/user-management-button/user-management-button.component';
import { LoginComponent } from './modules/home/pages/login/login.component';
import { RegisterComponent } from './modules/home/pages/register/register.component';
import { ProfileComponent } from './modules/home/pages/profile/profile.component';
import { LandingPageComponent } from './modules/home/pages/landing-page/landing-page.component';

import { UserService } from './core/services/user/user.service';
import { TweetService } from './core/services/tweet/tweet.service';
import { CommentComponent } from './shared/components/model/comment/comment.component';
import { NotFoundPageComponent } from './modules/home/pages/not-found-page/not-found-page.component';
import { TweetPageComponent } from './modules/home/pages/tweet-page/tweet-page.component';
import { TweetComponent } from './shared/components/model/tweet/tweet.component';
import { MatOptionModule } from '@angular/material/core';
import { CommentTimelineComponent } from './shared/components/center-panel/comment-timeline/comment-timeline.component';
import { ConfirmationDialogComponent } from './shared/components/dialog/confirmation-dialog/confirmation-dialog.component';
import { GenericButtonComponent } from './shared/components/button/generic-button/generic-button.component';
import { httpInterceptorProviders } from './core/interceptors/http-request/http-request.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    NavigationButtonComponent,
    NavigationPanelComponent,
    SearchNewsPanelComponent,
    UserComponent,
    TweetTimelineComponent,
    ComposeTweetComponent,
    LoginComponent,
    UserManagementButtonComponent,
    RegisterComponent,
    ProfileComponent,
    LandingPageComponent,
    CommentComponent,
    NotFoundPageComponent,
    TweetPageComponent,
    TweetComponent,
    CommentTimelineComponent,
    ConfirmationDialogComponent,
    GenericButtonComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatOptionModule,
    MatSelectModule,
  ],
  providers: [
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
    httpInterceptorProviders,
    UserService,
    TweetService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

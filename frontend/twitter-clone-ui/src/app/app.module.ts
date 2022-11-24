import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AppRoutingModule } from './app-routing.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';

import { AppComponent } from './app.component';
import { HeaderComponent } from './core/header/header.component';
import { FooterComponent } from './core/footer/footer.component';
import { HomeComponent } from './modules/home/pages/home/home.component';
import { NavigationButtonComponent } from './shared/components/navigation-button/navigation-button.component';
import { NavigationPanelComponent } from './shared/components/navigation-panel/navigation-panel.component';
import { SearchNewsPanelComponent } from './shared/components/search-news-panel/search-news-panel.component';
import { DialogBoxComponent } from './shared/components/dialog-box/dialog-box.component';
import { UserComponent } from './shared/components/user/user.component';
import { TimelineComponent } from './shared/components/timeline/timeline.component';
import { ComposeTweetComponent } from './shared/components/compose-tweet/compose-tweet.component';
import { UserManagementButtonComponent } from './shared/components/user-management-button/user-management-button.component';
import { LoginComponent } from './modules/home/pages/login/login.component';
import { RegisterComponent } from './modules/home/pages/register/register.component';
import { ProfileComponent } from './modules/home/pages/profile/profile.component';
import { LandingPageComponent } from './modules/home/pages/landing-page/landing-page.component';

import { UserService } from './core/services/user/user.service';
import { TweetService } from './core/services/tweet/tweet.service';
import { CommentComponent } from './shared/components/comment/comment.component';
import { NotFoundPageComponent } from './modules/home/pages/not-found-page/not-found-page.component';
import { TweetComponent } from './modules/home/pages/tweet/tweet.component';
import { MainTweetComponent } from './shared/components/main-tweet/main-tweet.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    NavigationButtonComponent,
    NavigationPanelComponent,
    SearchNewsPanelComponent,
    DialogBoxComponent,
    UserComponent,
    TimelineComponent,
    ComposeTweetComponent,
    LoginComponent,
    UserManagementButtonComponent,
    RegisterComponent,
    ProfileComponent,
    LandingPageComponent,
    CommentComponent,
    NotFoundPageComponent,
    TweetComponent,
    MainTweetComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatProgressSpinnerModule,
    MatCardModule
  ],
  providers: [UserService, TweetService],
  bootstrap: [AppComponent],
})
export class AppModule {}

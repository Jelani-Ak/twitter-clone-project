import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from "@angular/common/http";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {AppRoutingModule} from './app-routing.module';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatDialogModule} from "@angular/material/dialog";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


import {AppComponent} from './app.component';
import {HeaderComponent} from './core/components/header/header.component';
import {FooterComponent} from './core/components/footer/footer.component';
import {HomeComponent} from './modules/home/pages/home/home.component';
import {NavigationButtonComponent} from './shared/components/navigation-button/navigation-button.component';
import {NavigationPanelComponent} from './core/components/navigation-panel/navigation-panel.component';
import {SearchNewsPanelComponent} from './core/components/search-news-panel/search-news-panel.component';
import {DialogCreateTweetComponent} from './core/components/dialog-create-tweet/dialog-create-tweet.component';
import {UserComponent} from './shared/components/models/user/user.component';
import {TweetComponent} from './shared/components/models/tweet/tweet.component';
import {MatTableModule} from "@angular/material/table";
import {UserService} from "./core/services/user/user.service";
import {TweetService} from "./core/services/tweet/tweet.service";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    NavigationButtonComponent,
    NavigationPanelComponent,
    SearchNewsPanelComponent,
    DialogCreateTweetComponent,
    UserComponent,
    TweetComponent,
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
    MatTableModule

  ],
  providers: [UserService, TweetService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

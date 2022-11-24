import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './modules/home/pages/home/home.component';
import { LandingPageComponent } from './modules/home/pages/landing-page/landing-page.component';
import { LoginComponent } from './modules/home/pages/login/login.component';
import { NotFoundPageComponent } from './modules/home/pages/not-found-page/not-found-page.component';
import { RegisterComponent } from './modules/home/pages/register/register.component';
import { TweetComponent } from './modules/home/pages/tweet/tweet.component';
import { CommentComponent } from './shared/components/comment/comment.component';
import { UserComponent } from './shared/components/user/user.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'admin', component: UserComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'landing-page', component: LandingPageComponent },

  { path: 'tweet/:id', component: TweetComponent, children: [
    { path: 'comment/:id', component: CommentComponent},    
  ]},
  
  { path: '**', component: NotFoundPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './authorization/register/register.component';
import { LoginComponent } from './authorization/login/login.component';
import { MainComponent } from './main/main.component';
import { CreatePostComponent } from './post/create-post/create-post.component';
import { CreateTagComponent } from './tag/create-tag/create-tag.component';
import { DisplayTagsComponent } from './tag/display-tags/display-tags.component';
import { DisplayPostComponent } from './post/display-post/display-post.component';
import { UserPanelComponent } from './navbar/user-panel/user-panel.component';
import { RulesComponent } from './shared/rules/rules.component';
import { PrivacyPolicyComponent } from './shared/privacy-policy/privacy-policy.component';
import { AboutUsComponent } from './shared/about-us/about-us.component';
import { CommentsComponent } from './comments/comments.component';
import {ViewByTagComponentComponent} from './view-by-tag-component/view-by-tag-component.component';


const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'rejestracja', component: RegisterComponent},
  {path: 'zaloguj', component: LoginComponent},
  {path: 'stworz-wpis', component: CreatePostComponent},
  {path: 'stworz-tag', component: CreateTagComponent},
  {path: 'lista-tagow', component: DisplayTagsComponent},
  {path: 'wpis/:id', component: DisplayPostComponent},
  {path: 'uzytkownicy/:name', component: UserPanelComponent},
  {path: 'regulamin', component: RulesComponent},
  {path: 'polityka-prywatnosci', component: PrivacyPolicyComponent},
  {path: 'o-nas', component: AboutUsComponent},
  {path: 'komentarze-uzytkownika/:name', component: CommentsComponent},
  {path: 'wyswietl-tag/:name', component: ViewByTagComponentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

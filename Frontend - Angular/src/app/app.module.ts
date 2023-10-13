import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {LoginComponent} from './authorization/login/login.component';
import {RegisterComponent} from './authorization/register/register.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgxWebstorageModule, StrategyCacheService, StrategyIndex} from 'ngx-webstorage';
import {SimpleNotificationsModule} from 'angular2-notifications';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TokenCaptor} from './TokenCaptor';
import {MainComponent} from './main/main.component';
import {PostBoxComponent} from './shared/post-box/post-box.component';
import {RateButtonComponent} from './shared/rate-button/rate-button.component';
import {SideBarComponent} from './shared/side-bar/side-bar.component';
import {TagSideBarComponent} from './shared/tag-side-bar/tag-side-bar.component';
import {CreateTagComponent} from './tag/create-tag/create-tag.component';
import {CreatePostComponent} from './post/create-post/create-post.component';
import {DisplayTagsComponent} from './tag/display-tags/display-tags.component';
import {EditorModule} from '@tinymce/tinymce-angular';
import {DisplayPostComponent} from './post/display-post/display-post.component';
import {CommentsComponent} from './comments/comments.component';
import {NgbDropdownModule, NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {UserPanelComponent} from './navbar/user-panel/user-panel.component';
import {LeftPanelComponent} from './shared/left-panel/left-panel.component';
import {SocialMediaComponent} from './shared/social-media/social-media.component';
import {RulesComponent} from './shared/rules/rules.component';
import {PrivacyPolicyComponent} from './shared/privacy-policy/privacy-policy.component';
import {AboutUsComponent} from './shared/about-us/about-us.component';
import {ViewByTagComponentComponent} from './view-by-tag-component/view-by-tag-component.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    MainComponent,
    PostBoxComponent,
    RateButtonComponent,
    SideBarComponent,
    TagSideBarComponent,
    CreateTagComponent,
    CreatePostComponent,
    DisplayTagsComponent,
    DisplayPostComponent,
    CommentsComponent,
    UserPanelComponent,
    LeftPanelComponent,
    SocialMediaComponent,
    RulesComponent,
    PrivacyPolicyComponent,
    AboutUsComponent,
    ViewByTagComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot(),
    SimpleNotificationsModule.forRoot(),
    BrowserAnimationsModule,
    EditorModule,
    NgbModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenCaptor,
    multi: true
  },
    StrategyIndex,
    StrategyCacheService],
  bootstrap: [AppComponent]
})
export class AppModule {
}

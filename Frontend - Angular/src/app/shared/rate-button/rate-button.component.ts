import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../post-model';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { RatePayload } from './rate-payload';
import { RateService } from '../rate.service';
import { AuthorizationService } from 'src/app/authorization/shared/authorization.service';
import { PostService } from '../post.service';
import { RateType } from './rate-type';
import {NotificationsService} from 'angular2-notifications';

@Component({
  selector: 'app-rate-button',
  templateUrl: './rate-button.component.html',
  styleUrls: ['./rate-button.component.css']
})
export class RateButtonComponent implements OnInit {

  @Input() post: PostModel;
  ratePayload: RatePayload;
  upvoteColor: string;
  downvoteColor: string;


  constructor(private rateService: RateService,
              private authorizationService: AuthorizationService,
              private postService: PostService,
              private service: NotificationsService) {

    this.ratePayload = {
      rateType: undefined,
      postId: undefined
    };
  }

  ngOnInit() {
    this.updateVoteDetails();
  }


  upvotePost() {
    this.rateService.upvote(this.post.id).subscribe(
      () => {
        this.updateVoteDetails();
      },
      error => {
        console.log(error.error);
        this.onError(error.error);
      }
    );
  }

  downvotePost() {
    this.rateService.downvote(this.post.id).subscribe(
      () => {
        this.updateVoteDetails();
      },
      error => {
        this.onError(error.error);
      }
    );
  }

  private updateVoteDetails() {
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    });
  }

  onError(message) {
    this.service.error('Błąd!', message, {
      position: ['bottom', 'right'],
      timeOut: 1000,
      animate: 'fade',
      // showProgressBar: true
    });
  }

  isLoggedIn() {
    return this.authorizationService.isLoggedIn();
  }
}

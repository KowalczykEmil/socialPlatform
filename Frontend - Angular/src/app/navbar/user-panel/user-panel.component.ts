import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { AuthorizationService } from 'src/app/authorization/shared/authorization.service';
import {CommentPayload} from 'src/app/comments/comment-payload';
import {CommentsService} from 'src/app/comments/comments.service';
import {PostModel} from 'src/app/shared/post-model';
import {PostService} from 'src/app/shared/post.service';

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css']
})
export class UserPanelComponent implements OnInit {

  name: string;
  posts: PostModel[];
  comments: CommentPayload[];
  postLength: number;
  commentLength: number;
  sum: number;

  constructor(private activatedRoute: ActivatedRoute, private postService: PostService,
              private commentsService: CommentsService, private authorizationService: AuthorizationService, private router: Router) {
    this.name = this.activatedRoute.snapshot.params.name;

    this.postService.getAllPostsByUser(this.name).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
    });
    this.commentsService.getAllCommentsByUser(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });


  }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {

    this.postService.getAllPostsByUser(this.name).subscribe((posts) => {
      this.postLength = posts.length;
      this.calculateSum();
    });

    this.commentsService.getAllCommentsByUser(this.name).subscribe((comments) => {
      this.commentLength = comments.length;
      this.calculateSum();
    });
  }

  calculateSum() {
    if (this.postLength != undefined && this.comments != undefined) {
      this.sum = this.postLength + this.commentLength;
    }
  }

  goToUserComments() {
    this.router.navigateByUrl('/komentarze-uzytkownika/' + this.name);
  }

  getUsername() {
    return this.authorizationService.getUserName();
  }

}

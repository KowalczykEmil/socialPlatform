import { Component, OnInit } from '@angular/core';
import { CommentPayload } from './comment-payload';
import { CommentsService } from './comments.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorizationService } from '../authorization/shared/authorization.service';
import { PostService } from '../shared/post.service';
import { PostModel } from '../shared/post-model';

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  styleUrls: ['./comments.component.css']
})
export class CommentsComponent implements OnInit {
  name: string;
  comments: CommentPayload[];
  commentLength: number;
  username: string;

  constructor(
    private activatedRoute: ActivatedRoute,
    private commentsService: CommentsService,
    private router: Router
  ) {
    this.name = this.activatedRoute.snapshot.params.name;
    console.log(this.name);
  }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    this.commentsService.getAllCommentsByUser(this.name).subscribe((comments) => {
      this.comments = comments;
      this.commentLength = comments.length;
    });
  }


  goToUserProfile(){
    this.router.navigateByUrl('/uzytkownicy/'+ this.name)
  }

}

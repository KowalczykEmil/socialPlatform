import {Component, OnInit} from '@angular/core';
import {PostModel} from '../shared/post-model';
import {PostService} from '../shared/post.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  posts: Array<PostModel> = [];

  constructor(private postService: PostService) {
  }

  ngOnInit(): void {
    this.postService.getAllPosts().subscribe(post => {
      this.posts = post;
    });
  }

}

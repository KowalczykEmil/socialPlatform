import {Component, Input, OnInit} from '@angular/core';
import {PostService} from '../shared/post.service';
import {ActivatedRoute} from '@angular/router';
import {PostModel} from '../shared/post-model';

@Component({
  selector: 'app-view-by-tag-component',
  templateUrl: './view-by-tag-component.component.html',
  styleUrls: ['./view-by-tag-component.component.css']
})
export class ViewByTagComponentComponent implements OnInit {

  posts: Array<PostModel>;
  @Input()
  private tagName: string;

  constructor(private postService: PostService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.subscribe((param) => {
      this.tagName = param.name;
      this.postService.getPostsByTag(this.tagName).subscribe(
        data => {
          this.posts = data;
        },
        error => {
          console.log(error);
        }
      );
    });
  }
}

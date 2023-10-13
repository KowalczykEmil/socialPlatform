import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { TagModel } from 'src/app/tag/tag-response';
import { CreatePostPayload } from './create-post-payload';
import { Router } from '@angular/router';
import { PostService } from 'src/app/shared/post.service';
import { TagService } from 'src/app/tag/tag.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  postPayload: CreatePostPayload;
  tags: Array<TagModel>;

  constructor(private router: Router, private postService: PostService,
              private tagService: TagService) {
    this.postPayload = {
      tagName: '',
      postName: '',
      url: '',
      description: ''
    };
  }

  ngOnInit() {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      tagName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.tagService.getAllTags().subscribe((data) => {
      this.tags = data;
    }, error => {
      throwError(error);
    });
  }

  createPost() {
    this.postPayload.tagName = this.createPostForm.get('tagName').value;
    this.postPayload.postName = this.createPostForm.get('postName').value;
    this.postPayload.url = this.createPostForm.get('url').value;
    this.postPayload.description = this.createPostForm.get('description').value;

    this.postService.createPost(this.postPayload).subscribe((data) => {
      this.router.navigateByUrl('/');
    }, error => {
      throwError(error);
    });
  }

  discardPost() {
    this.router.navigateByUrl('/');
  }

}

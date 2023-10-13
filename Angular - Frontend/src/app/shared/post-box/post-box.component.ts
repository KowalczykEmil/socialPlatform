import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../post-model';
import { faClock, faComments } from '@fortawesome/free-solid-svg-icons';
import { PostService } from '../post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-box',
  templateUrl: './post-box.component.html',
  styleUrls: ['./post-box.component.css'],
})
export class PostBoxComponent implements OnInit {

  constructor(private router: Router) {
  }

  @Input()
  posts: PostModel[] | null = null;


  ngOnInit(): void {
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/wpis/' + id);
  }
}

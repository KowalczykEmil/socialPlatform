import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import { TagModel } from '../tag-response';
import { TagService } from '../tag.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-display-tags',
  templateUrl: './display-tags.component.html',
  styleUrls: ['./display-tags.component.css']
})
export class DisplayTagsComponent implements OnInit {

  tags: Array<TagModel>;
  constructor(private tagService: TagService, private router: Router) { }

  ngOnInit() {
    this.tagService.getAllTags().subscribe(data => {
      this.tags = data;
    }, error => {
      throwError(error);
    });
  }
}

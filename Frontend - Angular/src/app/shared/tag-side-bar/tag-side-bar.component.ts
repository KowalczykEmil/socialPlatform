import { Component, OnInit } from '@angular/core';
import { TagModel } from 'src/app/tag/tag-response';
import { TagService } from 'src/app/tag/tag.service';

@Component({
  selector: 'app-tag-side-bar',
  templateUrl: './tag-side-bar.component.html',
  styleUrls: ['./tag-side-bar.component.css']
})
export class TagSideBarComponent implements OnInit {

  tags: Array<TagModel> | undefined;
  displayViewAll: boolean;

  constructor(private tagService: TagService) {
    this.tagService.getAllTags().subscribe(data => {
      if (data.length >= 4) {
        this.tags = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.tags = data;
      }
    });
  }

  ngOnInit() {
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthorizationService} from '../../authorization/shared/authorization.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {

  constructor(private router: Router, private authService: AuthorizationService) { }

  ngOnInit() {
  }

  CreatePost() {
    this.router.navigateByUrl('/stworz-wpis');
  }

  CreateTag() {
    this.router.navigateByUrl('/stworz-tag');
  }

  isAdmin() {
    return this.authService.hasAdminRole();
  }
}

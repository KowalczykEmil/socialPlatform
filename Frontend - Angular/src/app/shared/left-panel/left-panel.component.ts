import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthorizationService} from 'src/app/authorization/shared/authorization.service';

@Component({
  selector: 'app-left-panel',
  templateUrl: './left-panel.component.html',
  styleUrls: ['./left-panel.component.css']
})
export class LeftPanelComponent implements OnInit {
  username: string;

  constructor(private authorizationService: AuthorizationService, private router: Router) {
  }

  ngOnInit() {
    this.username = this.authorizationService.getUserName();
  }

  goToUserProfile() {
    this.router.navigateByUrl('/uzytkownicy/' + this.username);
  }

  logout() {
    this.authorizationService.logout();
    this.router.navigateByUrl('').then(() => {
      window.location.reload();
    });
  }
}

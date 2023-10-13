import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization/shared/authorization.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  // isLoggedIn: boolean;
  username: string;

  constructor(private authorizationService: AuthorizationService, private router: Router) { }

  ngOnInit() {
  }

  getUsername() {
    return this.authorizationService.getUserName();
  }

  goToUserProfile() {
    this.router.navigateByUrl('/uzytkownicy/' + this.username);
  }

  logout() {
      this.authorizationService.logout();
      // this.isLoggedIn = false;
      this.router.navigateByUrl('').then(() => {window.location.reload();
    });
  }

  loggedIn() {
    return this.authorizationService.isLoggedIn();
  }
}

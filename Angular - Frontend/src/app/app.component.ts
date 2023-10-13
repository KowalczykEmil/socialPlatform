import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularSocialPlatf';
  isLoginPage: boolean;
  isRegisterPage: boolean;

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isLoginPage = (event.url === '/zaloguj');
        this.isRegisterPage = (event.url === '/rejestracja');
      }
    });
  }

}

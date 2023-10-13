import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginRequestPayload } from './login-request.payload';
import { AuthorizationService } from '../shared/authorization.service';
import { NotificationsService } from 'angular2-notifications';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginRequestPayload: LoginRequestPayload;

  constructor(private authorizationService: AuthorizationService, private service: NotificationsService, private router: Router) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login() {
    this.loginRequestPayload.username = this.loginForm.get('username').value;
    this.loginRequestPayload.password = this.loginForm.get('password').value;

    this.authorizationService.login(this.loginRequestPayload).subscribe(data => {
      console.log('Zalogowano pomyślnie!');
      this.onSuccess('Udało się zalogować! :)');
      this.router.navigate(['']);
    },
    error => {
      console.log('Błąd logowania', error);
      this.onError('Nie udało się zalogować! :(');
    });
  }

  onSuccess(message) {
    this.service.success('Sukces!', message, {
    position: ['bottom', 'right'],
    timeOut: 10000,
    animate: 'fade',
    showProgressBar: true
    });
  }

  onError(message) {
    this.service.error('Błąd!', message, {
    position: ['bottom', 'right'],
    timeOut: 2000,
    animate: 'fade',
    showProgressBar: true
    });
  }

}

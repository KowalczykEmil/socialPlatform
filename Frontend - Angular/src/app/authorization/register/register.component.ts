import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegisterRequestPayload } from './register-request.payload';
import { AuthorizationService } from '../shared/authorization.service';
import { NotificationsService } from 'angular2-notifications';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {


  registerForm: FormGroup;
  registerRequestPayload: RegisterRequestPayload;

  constructor(private authorizationService: AuthorizationService, private service: NotificationsService, private router: Router) {
    this.registerRequestPayload = {
      username: '',
      email: '',
      password: ''
    };
   }

   ngOnInit() {
    this.registerForm = new FormGroup({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required)
    });
  }

  register() {
    this.registerRequestPayload.email = this.registerForm.get('email').value;
    this.registerRequestPayload.username = this.registerForm.get('username').value;
    this.registerRequestPayload.password = this.registerForm.get('password').value;

    this.authorizationService.signup(this.registerRequestPayload)
    .subscribe(data => {
      console.log(data);
      this.onSuccess('Udało się zarejestrować! :)');
      this.router.navigate(['/zaloguj']);
    }, error => {
      console.log('Błąd logowania', error);
      this.onError('Nie udało się zarejestrować! :(');
    });
}

onSuccess(message) {
  this.service.success('Sukces!', message, {
  position: ['bottom', 'right'],
  timeOut: 1000,
  animate: 'fade',
  showProgressBar: true
  });
}

onError(message) {
  this.service.error('Błąd!', message, {
  position: ['bottom', 'right'],
  timeOut: 1000,
  animate: 'fade',
  showProgressBar: true
  });

}

}


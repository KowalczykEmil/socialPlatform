import {EventEmitter, Injectable, Output} from '@angular/core';
import {RegisterRequestPayload} from '../register/register-request.payload';
import {Observable, throwError} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {LoginRequestPayload} from '../login/login-request.payload';
import {LocalStorageService} from 'ngx-webstorage';
import {LoginResponse} from '../login/login-response.payload';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  private baseUrl = 'http://ec2-52-50-47-121.eu-west-1.compute.amazonaws.com:8080';
  private localhostURL = 'http://localhost:8080';

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();


  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  };

  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) {
  }

  signup(registerRequestPayload: RegisterRequestPayload): Observable<any> {
    return this.httpClient.post(`${this.localhostURL}/api/v1/autoryzacja/rejestracja`, registerRequestPayload, {responseType: 'text'});
  }

  login(loginRequestPayload: LoginRequestPayload): Observable<boolean> {
    return this.httpClient.post<LoginResponse>(`${this.localhostURL}/api/v1/autoryzacja/zaloguj`, loginRequestPayload).pipe(map(data => {
      this.localStorage.store('authenticationToken', data.authenticationToken);
      this.localStorage.store('username', data.username);
      this.localStorage.store('refreshToken', data.refreshToken);
      this.localStorage.store('expiresAt', data.expiresAt);
      this.localStorage.store('role', JSON.parse(window.atob(data.authenticationToken.split('.')[1])).scope[0]);
      return true;
    }));
  }

  refreshToken() {
    return this.httpClient.post<LoginResponse>(`${this.localhostURL}/api/v1/autoryzacja/odswiez/token`,
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');

        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  logout() {
    this.httpClient.post(`${this.localhostURL}/api/v1/wyloguj`, this.refreshTokenPayload,
      {responseType: 'text'})
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      });
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
  }


  getJwtToken() {
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }

  getExpirationTime() {
    return this.localStorage.retrieve('expiresAt');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  hasAdminRole(): boolean {
    return this.localStorage.retrieve('role').includes('ROLE_ADMIN');
  }
}


import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError, BehaviorSubject} from 'rxjs';
import {LoginResponse} from './authorization/login/login-response.payload';
import {AuthorizationService} from './authorization/shared/authorization.service';
import {catchError, filter, switchMap, take} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TokenCaptor implements HttpInterceptor {

  isTokenRefreshing = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(public authorizationService: AuthorizationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.indexOf('odswiez') !== -1
      || req.url.indexOf('zaloguj') !== -1
      || req.url.includes('rejestracja')
      || (req.method === 'GET' && req.url.includes('wpisy'))
      || (req.method === 'GET' && req.url.includes('tagi'))) {
      return next.handle(req);
    }

    const jwtToken = this.authorizationService.getJwtToken();
    return next.handle(this.addToken(req, jwtToken)).pipe(
      catchError(error => {
        if (error instanceof HttpErrorResponse && error.status === 403) {
          return this.handleAuthErrors(req, next);
        } else {
          return throwError(error);
        }
      })
    );
  }

  private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.isTokenRefreshing) {
      this.isTokenRefreshing = true;
      this.refreshTokenSubject.next(null);

      return this.authorizationService.refreshToken().pipe(
        switchMap((refreshTokenResponse: LoginResponse) => {
          this.isTokenRefreshing = false;
          this.refreshTokenSubject.next(refreshTokenResponse.authenticationToken);
          return next.handle(this.addToken(req, refreshTokenResponse.authenticationToken));
        })
      );
    } else {
      return this.refreshTokenSubject.pipe(
        filter(result => result !== null),
        take(1),
        switchMap((res) => {
          return next.handle(this.addToken(req, this.authorizationService.getJwtToken()));
        })
      );
    }
  }

  private addToken(req: HttpRequest<any>, jwtToken: any) {
    return req.clone({
      headers: req.headers.set('Authorization', 'Bearer ' + jwtToken)
    });
  }

}

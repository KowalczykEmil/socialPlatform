import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RatePayload } from './rate-button/rate-payload';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RateService {

  private baseUrl = 'http://ec2-52-50-47-121.eu-west-1.compute.amazonaws.com:8080';
  private localhostURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  rate(ratePayload: RatePayload): Observable<any> {
    return this.http.post(`${this.localhostURL}/api/v1/oceny/`, ratePayload);
  }

  upvote(postId: number): Observable<any> {
    return this.http.post(`${this.localhostURL}/api/v1/oceny/post/` + postId + '/upvote', {});
  }

  downvote(postId: number): Observable<any> {
    return this.http.post(`${this.localhostURL}/api/v1/oceny/post/` + postId + '/downvote', {});
  }
}

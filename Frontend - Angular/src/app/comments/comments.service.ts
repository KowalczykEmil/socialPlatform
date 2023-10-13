import { Injectable } from '@angular/core';
import { CommentPayload } from './comment-payload';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  private baseUrl = 'http://ec2-52-50-47-121.eu-west-1.compute.amazonaws.com:8080';
  private localhostURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  getAllCommentsForPost(postId: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>(`${this.localhostURL}/api/v1/komentarze/dlaWpisu/` + postId);
  }

  postComment(commentPayload: CommentPayload): Observable<any> {
    return this.httpClient.post<any>(`${this.localhostURL}/api/v1/komentarze/`, commentPayload);
  }

  getAllCommentsByUser(name: string) {
    return this.httpClient.get<CommentPayload[]>(`${this.localhostURL}/api/v1/komentarze/dlaUzytkownika/` + name);
  }
}

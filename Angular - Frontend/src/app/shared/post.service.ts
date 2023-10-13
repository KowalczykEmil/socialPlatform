import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PostModel } from './post-model';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create-post-payload';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl = 'http://ec2-52-50-47-121.eu-west-1.compute.amazonaws.com:8080';
  private localhostURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllPosts(): Observable<Array<PostModel>> {
    return this.http.get<Array<PostModel>>(`${this.localhostURL}/api/v1/wpisy/`);
  }

  createPost(postPayload: CreatePostPayload): Observable<any> {
    return this.http.post(`${this.localhostURL}/api/v1/wpisy/`, postPayload);
  }

  getAllPostsByUser(name: string): Observable<PostModel[]> {
    return this.http.get<PostModel[]>(`${this.localhostURL}/api/v1/wpisy/dlaUzytkownika/` + name);
  }

  getPost(id: number): Observable<PostModel> {
    return this.http.get<PostModel>(`${this.localhostURL}/api/v1/wpisy/` + id);
  }

  getPostsByTag(name: any) {
    return this.http.get<Array<PostModel>>(`${this.localhostURL}/api/v1/wpisy/tag?tagName=` + name);
  }
}

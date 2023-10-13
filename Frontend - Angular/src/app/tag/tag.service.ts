import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TagModel } from './tag-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private baseUrl = 'http://ec2-52-50-47-121.eu-west-1.compute.amazonaws.com:8080';
  private localhostURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getAllTags(): Observable<Array<TagModel>> {
    return this.http.get<Array<TagModel>>(`${this.localhostURL}/api/v1/tagi`);
  }

  createTag(tagModel: TagModel): Observable<TagModel> {
    return this.http.post<TagModel>(`${this.localhostURL}/api/v1/tagi`,
      tagModel);
  }
}

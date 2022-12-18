import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private baseUrl = 'http://localhost:8080/api/v1/user/';

  constructor(private http: HttpClient) {}

  public findByUserId(userId: string): Observable<User> {
    return this.http.get<User>(this.baseUrl + userId + '/get-user');
  }
}

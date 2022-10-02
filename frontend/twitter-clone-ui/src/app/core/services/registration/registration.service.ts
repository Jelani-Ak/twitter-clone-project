import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../shared/models/user/user';

@Injectable({
  providedIn: 'root',
})
export class RegistrationService {
  private baseurl: String = 'http://localhost:8080/api/v1/user';

  constructor(private http: HttpClient) {}

  public logUserInFromRemote(user: User): Observable<User> {
    return this.http.post<User>(this.baseurl + '/login', user);
  }

  public registerUserFromRemote(user: User): Observable<User> {
    return this.http.post<User>(this.baseurl + '/register/create', user);
  }
}

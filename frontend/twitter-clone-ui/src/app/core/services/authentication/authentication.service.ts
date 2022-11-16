import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private baseurl: String = 'http://localhost:8080/api/v1/authentication';

  constructor(private http: HttpClient) {}

  public registerUserFromRemote(user: User): Observable<User> {
    return this.http.post<User>(this.baseurl + '/register', user);
  }

  public logUserInFromRemote(user: User): Observable<User> {
    return this.http.post<User>(this.baseurl + '/login', user);
  }

  public confirmUser(token: string): Observable<string> {
    let params = new HttpParams();
    params = params.append('token', token);
    
    return this.http.get<string>(this.baseurl + '/confirm', { params: params });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../shared/models/user';
import { SignInRequestDTO } from 'src/app/shared/dto/sign-in-request.dto';
import { SignUpRequestDTO } from 'src/app/shared/dto/sign-up-request.dto';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private readonly httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  private readonly baseurl: String =
    'http://localhost:8080/api/v1/authentication';

  constructor(private http: HttpClient) {}

  public signUpFromRemote(signUpRequest: SignUpRequestDTO): Observable<User> {
    return this.http.post<User>(
      this.baseurl + '/sign-up',
      {
        username: signUpRequest.username,
        password: signUpRequest.password,
        email: signUpRequest.email,
      },
      this.httpOptions
    );
  }

  public signInFromRemote(signInRequest: SignInRequestDTO): Observable<User> {
    return this.http.post<User>(
      this.baseurl + '/sign-in',
      {
        username: signInRequest.username,
        password: signInRequest.password,
      },
      this.httpOptions
    );
  }

  public logUserOutFromRemote(): Observable<Object> {
    return this.http.post(this.baseurl + '/sign-out', {});
  }

  public confirmUserFromRemote(token: string): Observable<string> {
    let params = new HttpParams().append('token', token);

    return this.http.get<string>(this.baseurl + '/confirm-token', { params: params });
  }

  public deleteTokenFromRemote(token: string): Observable<string> {
    return this.http.delete<string>(this.baseurl + '/delete-token', { body: token });
  }
}

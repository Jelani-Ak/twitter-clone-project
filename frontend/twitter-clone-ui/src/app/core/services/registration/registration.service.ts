import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../../shared/models/user/user";

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private baseurl: String = "http://localhost:8080/api/v1/register";

  constructor(private http: HttpClient) {
  }

  public logUserInFromRemote(user: User): Observable<any> {
    return this.http.post<any>(this.baseurl + "/login", user)
  }

  public registerUserFromRemote(user: User): Observable<any> {
    return this.http.post<any>(this.baseurl + "/user", user);
  }
}

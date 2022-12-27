import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { User, UserProfileDTO } from 'src/app/shared/models/user';

@Injectable({
  providedIn: 'root',
})
export class UserProfileService {
  private readonly baseUrl = 'http://localhost:8080/api/v1/user/profile';

  constructor(private http: HttpClient) {}

  public updateProfile(data: UserProfileDTO): Observable<User> {
    const url = this.baseUrl + '/update-user';
    return this.http.post<User>(url, data);
  }
}

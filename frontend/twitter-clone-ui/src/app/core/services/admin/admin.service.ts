import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ConfirmationToken } from 'src/app/shared/models/confirmationToken';
import { User } from 'src/app/shared/models/user';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8080/api/v1/admin/';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.baseUrl + 'get/all/users');
  }

  getAllConfirmationTokens(): Observable<ConfirmationToken[]> {
    return this.http.get<ConfirmationToken[]>(this.baseUrl + 'get/all/confirmationToken');
  }

  deleteUserFromRemote(userId: string): Observable<User> {
    return this.http.delete<User>(this.baseUrl + 'delete/' + userId);
  }
}

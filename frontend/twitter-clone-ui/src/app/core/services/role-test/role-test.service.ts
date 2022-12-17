import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';

@Injectable({
  providedIn: 'root',
})
export class RoleTestService {
  private baseUrl = 'http://localhost:8080/api/v1/test/';

  constructor(private http: HttpClient) {}

  public getPublicContent(): Observable<any> {
    return this.http.get(this.baseUrl + 'all', { responseType: 'text' });
  }

  public getUserBoard(): Observable<any> {
    return this.http.get(this.baseUrl + 'user', { responseType: 'text' });
  }

  public getModeratorBoard(): Observable<any> {
    return this.http.get(this.baseUrl + 'mod', { responseType: 'text' });
  }

  public getAdminBoard(): Observable<any> {
    return this.http.get(this.baseUrl + 'admin', { responseType: 'text' });
  }
}

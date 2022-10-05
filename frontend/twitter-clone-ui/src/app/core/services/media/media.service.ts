import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Media } from 'src/app/shared/models/media/media';

@Injectable({
  providedIn: 'root',
})
export class MediaService {
  private baseURL = 'http://localhost:8080/api/v1/media';

  constructor(private httpClient: HttpClient) {}

  uploadMediaFromRemote(file: File): Observable<Media> {
    const formData = new FormData();
    formData.append('file', file, file.name);

    return this.httpClient.post<Media>(this.baseURL + '/upload', formData);
  }

  deleteMedia(mediaId: String): Observable<Media> {
    return this.httpClient.delete<Media>(this.baseURL + '/delete/' + mediaId);
  }

  getMedia(mediaId: String): Observable<Media> {
    return this.httpClient.get<Media>(this.baseURL + '/get/' + mediaId);
  }

  cancel() {
    throw new Error('Method not implemented.');
  }
}

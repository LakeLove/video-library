import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  getAllVideos(): Observable<video[]>{
    return this.httpClient.get<video[]>('api/videos/home')
  }
}

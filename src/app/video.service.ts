import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Video } from './video';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  getAllVideos(): Observable<Video[]>{
    return this.httpClient.get<Video[]>('api/videos/home')
  }
}

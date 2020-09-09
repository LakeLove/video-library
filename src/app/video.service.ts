import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Video } from './video';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  private videoUrl = 'https://channelcashmoney.herokuapp.com/api/videos/home';
  //private videoUrl = 'http://localhost:8080/api/videos/home';

  getAllVideos(): Observable<Video[]>{
    console.log("Fetching Videos")
    return this.httpClient.get<Video[]>(this.videoUrl)
  }
}

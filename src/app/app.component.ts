import { Component } from '@angular/core';
import { Video } from './video';
import { VideoService } from './video.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';
  videos: Video[] = []

  constructor(private videoService: VideoService) { }

  getVideos(){
  this.videoService.getVideos()
        .subscribe(videos => this.videos = videos.slice(1, 10));
  }
}

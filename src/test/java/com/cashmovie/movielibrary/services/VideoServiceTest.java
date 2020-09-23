package com.cashmovie.movielibrary.services;

import com.cashmovie.movielibrary.entities.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VideoServiceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VideoService testService;

    @Test
    public void getVideo_None() throws Exception {
        Long givenVideoID = 1L;
        when(testService.getVideoById(givenVideoID)).thenReturn(null);
        String expectedVideo = "";
        this.mvc.perform(get("/api/videos/id="+ givenVideoID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedVideo));
    }

    @Test
    public void getVideo_Exists() throws Exception {
        Video testVideo = new Video("Video", "/videos/1.mp4", "Frank", "Test");
        Long givenVideoID = 1L;
        when(testService.getVideoById(givenVideoID)).thenReturn(testVideo);
        String expectedVideo = "{\"id\":null,\"title\":\"Video\",\"filePath\":\"/videos/1.mp4\"," +
                "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}";
        this.mvc.perform(get("/api/videos/id="+ givenVideoID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedVideo));
    }

    @Test
    public void getAll() throws Exception {
        Video testVideo1 = new Video("Video1", "/videos/1.mp4", "Frank", "Test");
        Video testVideo2 = new Video("Video2", "/videos/2.mp4", "Frank", "Test");
        Long givenVideoID1 = 1L;
        Long givenVideoID2 = 2L;
        when(testService.getRecent()).thenReturn(new ArrayList<>(Arrays.asList(testVideo1, testVideo2)));
        String expectedVideo = "[{\"id\":null,\"title\":\"Video1\",\"filePath\":\"/videos/1.mp4\"," +
                "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}," +
                "{\"id\":null,\"title\":\"Video2\",\"filePath\":\"/videos/2.mp4\"," +
                "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}]";
        this.mvc.perform(get("/api/videos/home"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedVideo));
    }

    @Test
    public void postVideo() throws Exception {
        Long givenVideoID = 1L;
        Video testVideo = new Video("Video", "/videos/1.mp4", "Frank", "Test");
        Mockito.when(testService.postNewVideo(Mockito.any(Video.class))).thenReturn(testVideo);
        String expectedVideo = "{\"id\":null,\"title\":\"Video\",\"filePath\":\"/videos/1.mp4\"," +
                "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .post("/api/videos/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expectedVideo))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andExpect(content().string(expectedVideo));
    }

    @Test
    public void deleteVideoComment() throws Exception {
        Long givenVideoID = 1L;
        Video testVideo = new Video("Video", "/videos/1.mp4", "Frank", "Test");
        testVideo.setId(givenVideoID);
        String expectedVideo = "{\"id\":1,\"title\":\"Video\",\"filePath\":\"/videos/1.mp4\"," +
                "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}";
        when(testService.postNewVideo(testVideo)).thenReturn(testVideo);
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/api/videos/id=" + givenVideoID))
                .andExpect(status().isOk());
        verify(testService, times(1)).deleteVideoById(givenVideoID);
    }

  @Test
  public void getVideosByAuthor() throws Exception {
    Video testVideo1 = new Video("Video1", "/videos/1.mp4", "Frank", "Test");
    Video testVideo2 = new Video("Video2", "/videos/2.mp4", "Frank", "Test");
    String givenAuthor = "Frank";
    when(testService.getVideosByAuthor(givenAuthor)).thenReturn(new ArrayList<>(Arrays.asList(testVideo1, testVideo2)));
    String expectedVideos = "[{\"id\":null,\"title\":\"Video1\",\"filePath\":\"/videos/1.mp4\"," +
                             "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}," +
                             "{\"id\":null,\"title\":\"Video2\",\"filePath\":\"/videos/2.mp4\"," +
                             "\"author\":\"Frank\",\"date\":null,\"description\":\"Test\"}]";
    this.mvc.perform(get("/api/videos/user="+givenAuthor))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedVideos));
  }
}

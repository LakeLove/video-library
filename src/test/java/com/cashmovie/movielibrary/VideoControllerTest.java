package com.cashmovie.movielibrary;


import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cashmovie.movielibrary.controllers.VideoController;
import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.services.VideoService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static sun.plugin2.util.PojoUtil.toJson;

@WebMvcTest(VideoController.class)
public class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @Test
    public void contextLoads(){

    }

    @Test
    public void testPostVideo() throws Exception {
        Video testVideo = new Video("Title", "Filepath", "Author", "Description");

        when(videoService.postNewVideo(testVideo)).thenReturn(testVideo);
        this.mockMvc.perform(post("/api/videos/upload")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(testVideo)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetVideos() throws Exception {
        this.mockMvc.perform(get("/api/videos/home").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSingleVideo() throws Exception {
        this.mockMvc.perform(get("/api/videos/id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVideo() throws Exception {
        this.mockMvc.perform(delete("/api/videos/id=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}

package com.cashmovie.movielibrary;

import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.services.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
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
        this.mvc.perform(get("/videos/id="+ givenVideoID))
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
        this.mvc.perform(get("/videos/id="+ givenVideoID))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedVideo));
    }
}

package com.cashmovie.movielibrary;

import com.cashmovie.movielibrary.entities.Comment;
import com.cashmovie.movielibrary.services.CommentService;
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
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentServiceTest {
  
  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private CommentService testService;
  
  @Test
  public void getVideoComments_None() throws Exception {
    Long givenVideoID = 1L;
    when(testService.findByVideoId(givenVideoID)).thenReturn(new ArrayList<>());
    String expectedComments = "[]";
    this.mvc.perform(get("/api/comments/id="+ givenVideoID))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedComments));
  }
  
  @Test
  public void getVideoComments_One() throws Exception {
    Comment givenComment = new Comment(1L, "this is a test");
    Long givenVideoID = 1L;
    when(testService.findByVideoId(givenVideoID)).thenReturn(new ArrayList<>(Collections.singleton(givenComment)));
    String expectedComments = "[{\"comment_id\":null,\"comment_text\":\"this is a test\",\"comment_timestamp\":null," +
                                "\"video_id\":1}]";
    this.mvc.perform(get("/api/comments/id="+ givenVideoID))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedComments));
  }
  
  @Test
  public void getAllComments() throws Exception {
    Comment givenComment1 = new Comment(1L, "this is a test");
    Comment givenComment2 = new Comment(2L, "this is still a test");
    when(testService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(givenComment1, givenComment2)));
    String expectedComments = "[{\"comment_id\":null,\"comment_text\":\"this is a test\",\"comment_timestamp\":null," +
                                "\"video_id\":1},{\"comment_id\":null,\"comment_text\":\"this is still a test\"," +
                                "\"comment_timestamp\":null,\"video_id\":2}]";
    this.mvc.perform(get("/api/comments/all"))
                       .andExpect(status().isOk())
                       .andExpect(content().string(expectedComments));
  }
  
  @Test
  public void postVideoComment() throws Exception {
    Long givenVideoID = 4L;
    Comment givenComment = new Comment(4L,"this is a test");
    Mockito.when(testService.save(eq(givenVideoID), (Mockito.any()))).thenReturn(givenComment);
    String expectedComment = "{\"comment_id\":null,\"comment_text\":\"this is a test\",\"comment_timestamp\":null," +
                                "\"video_id\":4}";
    this.mvc.perform(MockMvcRequestBuilders
                       .post("/api/comments/id="+givenVideoID)
                       .contentType(MediaType.APPLICATION_JSON)
                       .content("{\"comment_text\":\"this is a test\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(expectedComment));
  }
  
  @Test
  public void deleteVideoComment() throws Exception {
    Long givenVideoID = 4L;
    Long givenCommentID = 2L;
    Comment givenComment = new Comment(givenVideoID, givenCommentID, "this is a test");
    when(testService.save(givenVideoID, givenComment)).thenReturn(givenComment);
    this.mvc.perform(MockMvcRequestBuilders
                       .delete("/api/comments/id="+givenVideoID+"/id="+givenCommentID))
            .andExpect(status().isOk());
    verify(testService, times(1)).delete(givenVideoID, givenCommentID);
  }
  
  @Test
  public void deleteAllVideoComment() throws Exception {
    Long givenVideoID = 4L;
    Long givenCommentID = 2L;
    Comment givenComment = new Comment(givenVideoID, givenCommentID, "this is a test");
    when(testService.save(givenVideoID, givenComment)).thenReturn(givenComment);
    this.mvc.perform(MockMvcRequestBuilders
                       .delete("/api/comments/id="+givenVideoID))
            .andExpect(status().isOk());
    verify(testService, times(1)).deleteAllByVideoId(givenVideoID);
  }
}
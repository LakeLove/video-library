package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.entities.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CommentControllerTest {

  @Autowired
  private MockMvc mvc;
  
  @MockBean
  private CommentController testController;
  
  @Test
  public void getVideoComments_None() throws Exception {
    Long givenVideoID = 1L;
    when(testController.getVideoComments(givenVideoID)).thenReturn(new ArrayList<>());
    String expectedComments = "[]";
    
    mvc.perform(get("/api/comments/id="+ givenVideoID))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedComments));
  }
  
  @Test
  public void getAllComments() throws Exception {
    List<Comment> comments = Arrays.asList(
      new Comment(1L, "this is a test"),
      new Comment(2L, "this is still a test"));
    when(testController.getAllComments()).thenReturn(comments);
    String expectedComments = "[{\"comment_id\":null,\"comment_text\":\"this is a test\",\"comment_timestamp\":null," +
                                "\"video_id\":1},{\"comment_id\":null,\"comment_text\":\"this is still a test\"," +
                                "\"comment_timestamp\":null,\"video_id\":2}]";
    
    mvc.perform(get("/api/comments/all"))
           .andExpect(status().isOk())
           .andExpect(content().string(expectedComments));
  
    verify(testController, times(1)).getAllComments();
    verifyNoMoreInteractions(testController);
  }
  
  @Test
  public void postVideoComment() throws Exception {
    Long givenVideoID = 4L;
    Comment givenComment = new Comment(givenVideoID, "this is a test");
    when(testController.postVideoComment(eq(givenVideoID), (any()))).thenReturn(givenComment);
    String expectedComment = "{\"comment_id\":null,\"comment_text\":\"this is a test\",\"comment_timestamp\":null," +
                               "\"video_id\":4}";
  
    mvc.perform(MockMvcRequestBuilders
                  .post("/api/comments/id="+givenVideoID)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{\"comment_text\":\"this is a test\"}"))
       .andExpect(status().isOk())
       .andExpect(content().contentType("application/json"))
       .andExpect(content().string(expectedComment));
  }
  
  @Test
  public void deleteVideoComment_Success() throws Exception {
    Long givenVideoID = 4L;
    Long givenCommentID = 2L;
    Comment givenComment = new Comment(givenVideoID, givenCommentID, "this is a test");
    when(testController.postVideoComment(givenVideoID, givenComment)).thenReturn(givenComment);
  
    mvc.perform(MockMvcRequestBuilders
                  .delete("/api/comments/id="+givenVideoID+"/id="+givenCommentID))
       .andExpect(status().isOk());
    
    verify(testController, times(1)).deleteVideoComment(givenComment.getVideo_id(), givenComment.getComment_id());
    verifyNoMoreInteractions(testController);
  }
  
  @Test
  public void deleteAllVideoComments() throws Exception {
    Long givenVideoID = 4L;
    Long givenCommentID = 2L;
    Comment givenComment = new Comment(givenVideoID, givenCommentID, "this is a test");
    when(testController.postVideoComment(givenVideoID, givenComment)).thenReturn(givenComment);
    mvc.perform(MockMvcRequestBuilders
                       .delete("/api/comments/id="+givenVideoID))
            .andExpect(status().isOk());
    verify(testController, times(1)).deleteAllVideoComments(givenVideoID);
    verifyNoMoreInteractions(testController);
  }
}
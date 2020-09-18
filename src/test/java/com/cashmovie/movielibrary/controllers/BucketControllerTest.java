package com.cashmovie.movielibrary.controllers;


import com.cashmovie.movielibrary.entities.Video;
import com.cashmovie.movielibrary.services.AmazonService;
import com.cashmovie.movielibrary.services.VideoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class BucketControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AmazonService amazonService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Test
  public void testUpload() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
      "file",
      "hello.txt",
      MediaType.TEXT_PLAIN_VALUE,
      "Hello, World!".getBytes()
    );

    MockMvc webMockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    webMockMvc.perform(multipart("/storage/uploadFile")
      .file(file))
      .andExpect(status().isOk());
  }

  @Test
  public void testDelete() throws Exception {
    String testFilepath = "/test/filename.txt";

    MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    when(amazonService.deleteFileFromS3Bucket(testFilepath)).thenReturn(testFilepath);
    this.mockMvc.perform(delete("/storage/deleteFile").content(testFilepath))
      .andExpect(status().isOk())
      .andExpect(content().string(testFilepath));
  }
}

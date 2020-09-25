package com.cashmovie.movielibrary.services;

import com.cashmovie.movielibrary.entities.Video;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class VideoServiceSearchTest {

  @Autowired
  private VideoService testService;

  @Test
  public void testSearch1() throws Exception {
    Video video1 = new Video("Title", "", "Author", "");
    Video video2 = new Video("asdf", "", "qwert", "");
    List<Video> inputList = Lists.list(video1, video2);

    List<Video> expected = Lists.list(video1);

    List<Video> actual = this.testService.filterSearch("title", inputList);

    assertEquals(expected, actual);
  }

  @Test
  public void testSearch2() throws Exception {
    Video video1 = new Video("Title", "", "Author", "");
    Video video2 = new Video("title!", "", "qwert", "");
    List<Video> inputList = Lists.list(video1, video2);

    List<Video> expected = Lists.list(video1, video2);

    List<Video> actual = this.testService.filterSearch("title", inputList);

    assertEquals(expected, actual);
  }

  @Test
  public void testSearch3() throws Exception {
    Video video1 = new Video("Title", "", "fdhulmes", "");
    Video video2 = new Video("asdf", "", "lakegreene", "");
    List<Video> inputList = Lists.list(video1, video2);

    List<Video> expected = Lists.list(video1);

    List<Video> actual = this.testService.filterSearch("!fdhulmes!@", inputList);

    assertEquals(expected, actual);
  }
}

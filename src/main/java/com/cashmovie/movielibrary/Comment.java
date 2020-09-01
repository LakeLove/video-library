package com.cashmovie.movielibrary;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Comment {
  @Id
  @GeneratedValue (strategy = GenerationType.AUTO)
  private Long comment_id;
  private String comment_text;
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date comment_timestamp;
  private Long video_id;
  
  public Comment() {}
  
  public Long getComment_id() {
    return comment_id;
  }
  
  public void setComment_id(Long comment_id) {
    this.comment_id = comment_id;
  }
  
  public String getComment_text() {
    return comment_text;
  }
  
  public void setComment_text(String comment_text) {
    this.comment_text = comment_text;
  }
  
  public Date getComment_timestamp() {
    return comment_timestamp;
  }
  
  public void setComment_timestamp(Date comment_timestamp) {
    this.comment_timestamp = comment_timestamp;
  }
  
  public Long getVideo_id() {
    return video_id;
  }
  
  public void setVideo_id(Long video_id) {
    this.video_id = video_id;
  }
}

package com.cashmovie.movielibrary;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
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
  
  public Comment(String comment) {
    comment_text = comment;
  }
  
  public Comment(Long videoID, String comment) {
    video_id = videoID;
    comment_text = comment;
  }
  
  public Comment(Long videoID, Long ID, String comment) {
    video_id = videoID;
    comment_id = ID;
    comment_text = comment;
  }
  
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
  
  @Override
  public boolean equals(Object o) {
    if(this == o) { return true; }
    if(!(o instanceof Comment)) { return false; }
    Comment comment = (Comment) o;
    return Objects.equals(getComment_id(), comment.getComment_id()) && Objects.equals(getComment_text(),
      comment.getComment_text()) && Objects.equals(getComment_timestamp(), comment.getComment_timestamp()) && Objects.equals(getVideo_id(), comment.getVideo_id());
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(getComment_id(), getComment_text(), getComment_timestamp(), getVideo_id());
  }
}

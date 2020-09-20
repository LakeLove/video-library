package com.cashmovie.movielibrary.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserCred {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private Long user_id;

  private String username;
  private String password;

  public UserCred(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public UserCred() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

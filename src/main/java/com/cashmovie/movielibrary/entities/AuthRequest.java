package com.cashmovie.movielibrary.entities;

public class AuthRequest {

  private String username;
  private String password;

  public AuthRequest(String username, String password){
    this.password = password;
    this.username = username;
  }

  public AuthRequest() {
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

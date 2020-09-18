package com.cashmovie.movielibrary.entities;

public class Bearer {
  String access_token;
  String token_type;
  String scope;

  public Bearer(String access_token, String token_type, String scope) {
    this.access_token = access_token;
    this.token_type = token_type;
    this.scope = scope;
  }

  public Bearer() {}

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getToken_type() {
    return token_type;
  }

  public void setToken_type(String token_type) {
    this.token_type = token_type;
  }
}

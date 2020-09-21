package com.cashmovie.movielibrary.controllers;

import com.auth0.AuthenticationController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cashmovie.movielibrary.entities.User;
import com.cashmovie.movielibrary.services.AuthConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
@CrossOrigin
public class HomeController {
  private static ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private AuthConfig config;

  @Autowired
  private AuthenticationController authenticationController;

  @GetMapping (value = "/")
  @ResponseBody
  public String home(final Authentication authentication) throws IOException, UnirestException {
    TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
    if (token != null ) {
      DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
      String user_id = jwt.getClaims().get("sub").asString();
      return new ObjectMapper().writeValueAsString("Logged in as: "+getUsername(user_id));
    } else {
      return new ObjectMapper().writeValueAsString("You are not logged in.");
    }
  }

  @GetMapping (value = "/api/users/id={user_id}")
  @ResponseBody
  protected String getUsername(@PathVariable ("user_id") String user_id) throws IOException, UnirestException {
    String url = "https://channel-cashmoney.us.auth0.com/api/v2/users/"+user_id+"?fields=username&include_fields=true";
    HttpResponse<String> response = Unirest.get(url)
                                           .header("authorization", "Bearer "+config.getApiToken())
                                           .asString();
    String responseBody = response.getBody();
    return new ObjectMapper().readValue(responseBody, User.class).getUsername();
  }
}

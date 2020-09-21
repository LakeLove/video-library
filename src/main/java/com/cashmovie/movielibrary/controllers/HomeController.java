package com.cashmovie.movielibrary.controllers;

import com.auth0.AuthenticationController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
@Controller
@CrossOrigin
public class HomeController {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private AuthController authController;

  @Autowired
  private AuthenticationController authenticationController;

  @GetMapping (value = "/")
  @ResponseBody
  public String home(final Authentication authentication) throws IOException, UnirestException {
    TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
    if (token != null ) {
      DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
      String user_id = jwt.getClaims().get("sub").asString();
      return objectMapper.writeValueAsString("Logged in as: "+authController.getUsername(user_id));
    } else {
      return objectMapper.writeValueAsString("You are not logged in.");
    }
  }
}

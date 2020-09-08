package com.cashmovie.movielibrary.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class HomeController {
  @GetMapping (value = "/")
  @ResponseBody
  public String home(final Authentication authentication) {
    TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
    if (token != null ) {
      DecodedJWT jwt = JWT.decode(token.getCredentials()
                                       .toString());
      String email = jwt.getClaims()
                        .get("email")
                        .asString();
      return "Welcome, " + email + "! ";
    } else {
      return "You are not logged in: go <a href='/login'>login</a>";
    }
  }
}
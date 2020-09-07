package com.cashmovie.movielibrary.controllers;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.message.config.AuthConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class AuthController {
  @Autowired
  private AuthConfig config;
  
  @Autowired
  private AuthenticationController authenticationController;
  
  @GetMapping (value = "/login")
  protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String redirectUri = "http://localhost:8080/callback";
    String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                                                  .withScope("openid email")
                                                  .build();
    response.sendRedirect(authorizeUrl);
  }
  
  @GetMapping(value="/callback")
  public void callback(HttpServletRequest request, HttpServletResponse response) throws IdentityVerificationException {
    Tokens tokens = authenticationController.handle(request, response);
    
    DecodedJWT jwt = JWT.decode((tokens).getIdToken());
    TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
      jwt.getToken());
    authToken2.setAuthenticated(true);
    
    SecurityContextHolder.getContext().setAuthentication(authToken2);
    response.sendRedirect(config.getContextPath(request) + "/");
  }
  
  @Controller
  public class HomeController {
    @GetMapping(value = "/")
    @ResponseBody
    public String home(final Authentication authentication) {
      TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
      DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
      String email = jwt.getClaims().get("email").asString();
      return "Welcome, " + email + "!";
    }
  }
}

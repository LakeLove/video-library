package com.cashmovie.movielibrary.controllers;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cashmovie.movielibrary.entities.Bearer;
import com.cashmovie.movielibrary.entities.User;
import com.cashmovie.movielibrary.services.AuthConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@CrossOrigin
public class AuthController {
  @Autowired
  private AuthConfig config;

  @Autowired
  private AuthenticationController authenticationController;

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @GetMapping (value = "/login")
  protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // https://stackoverflow.com/a/57800880
    final String redirectUri = ServletUriComponentsBuilder.fromRequest(request).replacePath("callback").build().toUriString();
    String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                                                  .withScope("openid email")
                                                  .build();
    response.sendRedirect(authorizeUrl);
  }

  @GetMapping(value="/callback")
  public void callback(HttpServletRequest request, HttpServletResponse response) throws IdentityVerificationException, IOException {
    Tokens tokens = authenticationController.handle(request, response);

    DecodedJWT jwt = JWT.decode((tokens).getIdToken());
    TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
      jwt.getToken());
    authToken2.setAuthenticated(true);

    SecurityContextHolder.getContext().setAuthentication(authToken2);
    response.sendRedirect("/");
  }

  protected String getBearerToken() throws IOException, UnirestException {
    HttpResponse<String> response = Unirest.post("https://channel-cashmoney.us.auth0.com/oauth/token")
                                           .header("content-type", "application/json")
                                           .body("{\"client_id\":\""+config.getClientId()+"\"," +
                                                   "\"client_secret\":\""+config.getClientSecret()+"\"," +
                                                   "\"audience\":\"https://channel-cashmoney.us.auth0.com/api/v2/\"," +
                                                   "\"grant_type\":\"client_credentials\"}")
                                           .asString();
    String responseBody = response.getBody();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    System.out.println(objectMapper.readValue(responseBody, Bearer.class).getAccess_token());
    return objectMapper.readValue(responseBody, Bearer.class).getAccess_token();
  }

  @GetMapping (value = "/api/users/id={user_id}")
  @ResponseBody
  protected String getUsername(@PathVariable ("user_id") String user_id) throws IOException, UnirestException {
    String url = "https://channel-cashmoney.us.auth0.com/api/v2/users/"+user_id+"?fields=username&include_fields=true";
    HttpResponse<String> response = Unirest.get(url)
                                           .header("authorization", "Bearer "+this.getBearerToken())
                                           .asString();
    String responseBody = response.getBody();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    String username = objectMapper.readValue(responseBody, User.class).getUsername();
    return objectMapper.writeValueAsString(username);
  }
}

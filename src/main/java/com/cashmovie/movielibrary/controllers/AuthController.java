package com.cashmovie.movielibrary.controllers;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cashmovie.movielibrary.entities.Bearer;
import com.cashmovie.movielibrary.services.AuthConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
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

  private static ObjectMapper objectMapper = new ObjectMapper();

  String getBaseUrl(HttpServletRequest req) {
    return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
  }

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

  @GetMapping(value="/api/AUTH0_CLIENT_ID")
  @ResponseBody
  public String getClientId() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(config.getClientId());
  }

  @GetMapping(value="/api/AUTH0_DOMAIN")
  @ResponseBody
  public String getDomain() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(config.getDomain());
  }

  @GetMapping(value="/api/AUTH0_CLIENT_SECRET")
  @ResponseBody
  public String getClientSecret() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(config.getClientSecret());
  }

  @GetMapping(value="/api/AUTH0_API_TOKEN")
  @ResponseBody
  public String getApiToken() throws IOException, UnirestException {
    return new ObjectMapper().writeValueAsString(this.getTokens());
  }

  protected String getTokens() throws IOException, UnirestException {
    HttpResponse<String> response = Unirest.post("https://channel-cashmoney.us.auth0.com/oauth/token")
                                           .header("content-type", "application/json")
                                           .body("{\"client_id\":\""+config.getClientId()+"\"," +
                                                   "\"client_secret\":\""+config.getClientSecret()+"\"," +
                                                   "\"audience\":\"https://channel-cashmoney.us.auth0.com/api/v2/\"," +
                                                   "\"grant_type\":\"client_credentials\"}")
                                           .asString();
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    return objectMapper.readValue(responseBody, Bearer.class).getAccess_token();
  }
}

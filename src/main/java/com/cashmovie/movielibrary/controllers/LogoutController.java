package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.services.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
public class LogoutController implements LogoutSuccessHandler {
  @Autowired
  private AuthConfig config;
  
  @Value(value = "${com.auth0.domain}")
  private String domain;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                              Authentication authentication) throws IOException {
    if (request.getSession() != null) {
      request.getSession().invalidate();
    }
    String returnTo = ServletUriComponentsBuilder.fromRequest(request).replacePath(null).build().toUriString();
    String logoutUrl = config.getDomain() + "/v2/logout?client_id=" +
                        config.getClientId() + "&returnTo=" + returnTo;
    response.sendRedirect(logoutUrl);
  }
}

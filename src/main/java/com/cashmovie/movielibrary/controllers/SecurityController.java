package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.JwtUtil;
import com.cashmovie.movielibrary.entities.AuthRequest;
import com.cashmovie.movielibrary.entities.AuthResponse;
import com.cashmovie.movielibrary.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private MyUserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtTokenUtil;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
      );
    }
    catch (BadCredentialsException e) {
      throw new Exception("Incorrect username or password", e);
    }


    final UserDetails userDetails = userDetailsService
      .loadUserByUsername(authRequest.getUsername());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthResponse(jwt));
  }

}

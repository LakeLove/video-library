package com.cashmovie.movielibrary.controllers;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.cashmovie.movielibrary.JwtUtil;
import com.cashmovie.movielibrary.entities.AuthRequest;
import com.cashmovie.movielibrary.entities.AuthResponse;
import com.cashmovie.movielibrary.entities.UserCred;
import com.cashmovie.movielibrary.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<?> createAccount(@RequestBody UserCred credentials){
    try{
      return ResponseEntity.ok(this.userDetailsService.createUser(credentials));
    } catch (UsernameExistsException e){
      return ResponseEntity.badRequest().build();
    }

  }

}

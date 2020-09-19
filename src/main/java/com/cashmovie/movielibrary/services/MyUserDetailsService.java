package com.cashmovie.movielibrary.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class MyUserDetailsService implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    return new User("foo" , "foo", new ArrayList<>());
  }
}

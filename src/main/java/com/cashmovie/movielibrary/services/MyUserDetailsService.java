package com.cashmovie.movielibrary.services;

import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.cashmovie.movielibrary.entities.UserCred;
import com.cashmovie.movielibrary.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    UserCred user = findByUsername(userName);
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  private UserCred findByUsername(String userName){
    return this.userRepository.findByUsername(userName);
  }

  public UserCred createUser(UserCred credentials) throws UsernameExistsException {
    if(findByUsername(credentials.getUsername()) == null){
      return this.userRepository.save(credentials);
    }
    else{
      throw new UsernameExistsException("Username already exists");
    }
  }
}

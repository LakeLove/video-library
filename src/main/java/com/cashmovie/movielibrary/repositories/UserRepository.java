package com.cashmovie.movielibrary.repositories;

import com.cashmovie.movielibrary.entities.UserCred;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserCred, Long> {

  UserCred findByUsername(String userName);
}

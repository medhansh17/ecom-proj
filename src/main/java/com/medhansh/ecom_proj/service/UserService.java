package com.medhansh.ecom_proj.service;

import com.medhansh.ecom_proj.model.User;
import com.medhansh.ecom_proj.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepo repo;

  public UserService(UserRepo repo) {
    this.repo = repo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User u = repo.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return org.springframework.security.core.userdetails.User
        .withUsername(u.getUsername())
        .password(u.getPassword())
        .roles(u.getRoles().toArray(new String[0]))
        .build();
  }
}

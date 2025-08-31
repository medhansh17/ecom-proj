package com.medhansh.ecom_proj.controller;

import com.medhansh.ecom_proj.model.User;
import com.medhansh.ecom_proj.repo.UserRepo;
import com.medhansh.ecom_proj.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserRepo repo;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder encoder;

  public AuthController(UserRepo repo, JwtUtil jwtUtil, PasswordEncoder encoder) {
    this.repo = repo;
    this.jwtUtil = jwtUtil;
    this.encoder = encoder;
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody User user) {
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRoles(Set.of("ROLE_USER"));
    repo.save(user);
    return ResponseEntity.ok("User registered");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody User user) {
    User u = repo.findByUsername(user.getUsername()).orElseThrow();
    if (encoder.matches(user.getPassword(), u.getPassword())) {
      String token = jwtUtil.generateToken(u.getUsername(), u.getRoles());
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.status(401).body("Invalid credentials");
  }
}

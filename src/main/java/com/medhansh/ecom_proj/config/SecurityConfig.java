package com.medhansh.ecom_proj.config;

import com.medhansh.ecom_proj.service.UserService;
import com.medhansh.ecom_proj.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Configuration
public class SecurityConfig {

  private final JwtUtil jwtUtil;
  private final UserService userService;

  public SecurityConfig(JwtUtil jwtUtil, UserService userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/product/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/product/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/product/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtAuthFilter(jwtUtil, userService), UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // JWT Filter
  static class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    JwtAuthFilter(JwtUtil jwtUtil, UserService userService) {
      this.jwtUtil = jwtUtil;
      this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
      String authHeader = request.getHeader("Authorization");
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);
        if (username != null) {
          var userDetails = userService.loadUserByUsername(username);
          var auth = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }
      filterChain.doFilter(request, response);
    }
  }
}

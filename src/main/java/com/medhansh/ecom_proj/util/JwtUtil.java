package com.medhansh.ecom_proj.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {
  private final String SECRET = "SuperSecretKeyForJwtAuthMustBeAtLeast256BitsLong";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

  public String generateToken(String username, Set<String> roles) {
      // 1 hour
      long EXPIRATION = 1000 * 60 * 60;
      return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build()
        .parseClaimsJws(token).getBody().getSubject();
  }
}

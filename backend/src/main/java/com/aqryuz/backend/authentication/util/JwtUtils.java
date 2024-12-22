package com.aqryuz.backend.authentication.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.aqryuz.backend.authentication.config.JWTProperties;
import com.aqryuz.backend.authentication.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

  private final Long jwtExpirationMs;

  private final SecretKey jwtSecret; // Store as SecretKey

  public JwtUtils(JWTProperties jwt) {
    this.jwtSecret = Keys.hmacShaKeyFor(jwt.getSecret().getBytes()); // Initialize SecretKey
    this.jwtExpirationMs = jwt.getExpirationMs();
  }

  public String generateJwtToken(Authentication authentication) {
    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
    User user = (User) authentication.getPrincipal();

    try {
      return Jwts.builder()
          .claim(Claims.SUBJECT, userPrincipal.getUsername())
          .claim("userId", user.getId())
          .claim(
              "roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
          .claim(Claims.ISSUED_AT, new Date())
          .claim(Claims.EXPIRATION, new Date((new Date()).getTime() + jwtExpirationMs))
          .signWith(jwtSecret)
          .compact();
    } catch (JwtException e) {
      log.info("invalid signature");
      return null;
    }
  }
}

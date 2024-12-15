package com.aqryuz.backend.authentication.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  private final SecretKey jwtSecret; // Store as SecretKey

  public JwtUtils(@Value("${jwt.secret}") String jwtSecretString) {
    this.jwtSecret = Keys.hmacShaKeyFor(jwtSecretString.getBytes()); // Initialize SecretKey

  }

  public String generateJwtToken(Authentication authentication) {
    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

    try {
      return Jwts.builder()
          .claim(Claims.SUBJECT, userPrincipal.getUsername())
          .claim(Claims.ISSUED_AT, new Date()) // Use claim() with Claims.ISSUED_AT
          .claim(Claims.EXPIRATION, new Date((new Date()).getTime() + jwtExpirationMs)) // Also use claim for expiration
                                                                                        // for consistency
          .signWith(jwtSecret)
          .compact();

    } catch (JwtException e) {
      log.info("invalid signature");
      return null;
    }
  }
}
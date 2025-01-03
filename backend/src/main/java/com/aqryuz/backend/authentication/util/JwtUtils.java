package com.aqryuz.backend.authentication.util;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;
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

  private final SecretKey jwtSecret;

  public JwtUtils(JWTProperties jwt) {
    this.jwtSecret = Keys.hmacShaKeyFor(jwt.getSecret().getBytes());
    this.jwtExpirationMs = jwt.getExpirationMs();
  }

  @Nullable
  public String generateJwtToken(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    List<String> roles =
        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    try {
      return Jwts.builder()
          .subject(user.getUsername())
          .claim("userId", user.getId())
          .claim("roles", roles)
          .issuedAt(new Date())
          .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
          .signWith(jwtSecret)
          .compact();
    } catch (JwtException e) {
      log.error("Error generating JWT token: {}", e.getMessage());
      return null;
    }
  }

  public String getUsernameFromToken(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUsernameFromToken(token);
    return username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    Date expiration = extractClaim(token, Claims::getExpiration);
    return expiration == null || expiration.before(new Date());
  }

  @Nullable
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    try {
      Claims claims =
          Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token).getPayload();
      return claimsResolver.apply(claims);
    } catch (JwtException e) {
      log.error("Error extracting claim from JWT: {}", e.getMessage());
      return null;
    }
  }
}

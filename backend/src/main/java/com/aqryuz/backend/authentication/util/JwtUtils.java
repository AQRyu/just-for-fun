package com.aqryuz.backend.authentication.util;

import com.aqryuz.backend.authentication.config.JWTProperties;
import com.aqryuz.backend.authentication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

  @Nullable
  public String extractJwtToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    } else {
      return null;
    }
  }

  public String extractJwtToken(ServerHttpRequest request) {
    List<String> authHeaders = request.getHeaders().get("Authorization");
    if (authHeaders != null && !authHeaders.isEmpty()) {
      String token = authHeaders.get(0);
      if (token.startsWith("Bearer ")) {
        return token.substring(7);
      }
    }
    return null;
  }

  @Nullable
  public String extractJwtToken(StompHeaderAccessor request) {
    final String authorizationHeader = request.getFirstNativeHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    } else {
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
    Claims claims =
        Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token).getPayload();
    return claimsResolver.apply(claims);
  }
}

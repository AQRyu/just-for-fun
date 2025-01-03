package com.aqryuz.backend.authentication.util;

import com.aqryuz.backend.authentication.config.JWTProperties;
import com.aqryuz.backend.authentication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
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

  public String getUsernameFromToken(String token) {
    try {
      Claims claims = getAllClaims(token);

      return claims.get("userId", String.class);

    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return null;
  }

  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }

  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaims(String token) {
    return Jwts.parser().verifyWith(jwtSecret).build().parseSignedClaims(token).getPayload();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}

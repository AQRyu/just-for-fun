package com.aqryuz.backend.authentication.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandler {

  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token expired");
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<String> handleJwtException(JwtException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unknown JWT token error");
  }
}

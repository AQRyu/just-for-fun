package com.aqryuz.backend.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProfileGlobalExceptionHandler {
  @ExceptionHandler(UserProfileNotFoundException.class)
  public ResponseEntity<String> handleProfileNotFound(UserProfileNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }
}

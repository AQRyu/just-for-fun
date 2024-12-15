package com.aqryuz.backend.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aqryuz.backend.authentication.dto.LoginRequest;
import com.aqryuz.backend.authentication.dto.RegistrationRequest;
import com.aqryuz.backend.authentication.dto.User;
import com.aqryuz.backend.authentication.dto.UserRegistrationResponse;
import com.aqryuz.backend.authentication.exception.DuplicateUsernameException;
import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> register(@RequestBody RegistrationRequest request) {
    User registeredUser = userService.registerUser(request);
    UserRegistrationResponse response = userMapper.userToUserRegistrationResponse(registeredUser);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    // ... call authenticationService.login(request)
    return null;
  }

  @ExceptionHandler(DuplicateUsernameException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
  public String handleDuplicateUsername(DuplicateUsernameException ex) {
    return ex.getMessage(); // Return the exception message
  }
}

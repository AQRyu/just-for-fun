package com.aqryuz.backend.authentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aqryuz.backend.authentication.controller.payload.LoginRequest;
import com.aqryuz.backend.authentication.controller.payload.LoginResponse;
import com.aqryuz.backend.authentication.controller.payload.RegistrationRequest;
import com.aqryuz.backend.authentication.controller.payload.UserRegistrationResponse;
import com.aqryuz.backend.authentication.exception.DuplicateUsernameException;
import com.aqryuz.backend.authentication.mapper.UserMapper;
import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.authentication.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final UserMapper userMapper;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> register(@RequestBody RegistrationRequest request) {
    User registeredUser = userService.registerUser(request);
    UserRegistrationResponse response = userMapper.userToUserRegistrationResponse(registeredUser);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.username(), request.password()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      LoginResponse response = new LoginResponse(jwt);
      return ResponseEntity.ok(response);

    } catch (Exception ex) {
      // Handle authentication exceptions (e.g., BadCredentialsException)
      return ResponseEntity.status(401).build(); // Or return a more specific error response

    }
  }

  @ExceptionHandler(DuplicateUsernameException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Bad Request
  public String handleDuplicateUsername(DuplicateUsernameException ex) {
    return ex.getMessage(); // Return the exception message
  }
}

package com.aqryuz.backend.authentication.controller;

import com.aqryuz.backend.authentication.controller.payload.LoginRequest;
import com.aqryuz.backend.authentication.controller.payload.LoginResponse;
import com.aqryuz.backend.authentication.controller.payload.RegistrationRequest;
import com.aqryuz.backend.authentication.controller.payload.UserRegistrationResponse;
import com.aqryuz.backend.authentication.mapper.UserMapper;
import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.service.LoginService;
import com.aqryuz.backend.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final UserService userService;
  private final LoginService loginService;
  private final UserMapper userMapper;

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationResponse> register(
      @RequestBody RegistrationRequest request) {
    User registeredUser = userService.registerUser(request);
    UserRegistrationResponse response = userMapper.userToUserRegistrationResponse(registeredUser);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public LoginResponse login(@RequestBody LoginRequest request) {
    return loginService.login(request);
  }
}

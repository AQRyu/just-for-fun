package com.aqryuz.backend.authentication.service;

import com.aqryuz.backend.authentication.controller.payload.LoginRequest;
import com.aqryuz.backend.authentication.controller.payload.LoginResponse;
import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.authentication.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public LoginResponse login(LoginRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    User user =
        userRepository
            .findByUsername(request.username())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new LoginResponse(jwt, user.getId(), user.getUsername(), user.getRole());
  }
}

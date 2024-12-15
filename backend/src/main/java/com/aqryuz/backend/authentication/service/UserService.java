package com.aqryuz.backend.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aqryuz.backend.authentication.dto.RegistrationRequest;
import com.aqryuz.backend.authentication.dto.Role;
import com.aqryuz.backend.authentication.dto.User;
import com.aqryuz.backend.authentication.exception.DuplicateUsernameException;
import com.aqryuz.backend.authentication.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User registerUser(RegistrationRequest request) {
    // Check if username already exists
    if (userRepository.findByUsername(request.username()).isPresent()) {
      throw new DuplicateUsernameException(request.username());
    }

    // ... other validation logic (email, password strength, etc.)
    User user = User.builder()
        .username(request.username())
        .password(passwordEncoder.encode(request.password()))
        .email(request.email())
        .role(Role.USER) // Set default role
        .build();
    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}

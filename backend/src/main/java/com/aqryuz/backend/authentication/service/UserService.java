package com.aqryuz.backend.authentication.service;

import com.aqryuz.backend.authentication.controller.payload.RegistrationRequest;
import com.aqryuz.backend.authentication.exception.DuplicateUsernameException;
import com.aqryuz.backend.authentication.model.Role;
import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User registerUser(RegistrationRequest request) {
    if (userRepository.findByUsername(request.username()).isPresent()) {
      throw new DuplicateUsernameException();
    }

    User user =
        User.builder()
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .email(request.email())
            .role(Role.USER)
            .build();
    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public Optional<User> findById(Long userId) {
    return userRepository.findById(userId);
  }
}

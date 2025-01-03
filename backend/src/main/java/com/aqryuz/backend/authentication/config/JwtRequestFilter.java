package com.aqryuz.backend.authentication.config;

import java.io.IOException;

import javax.annotation.Nullable;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.authentication.util.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwtToken = extractJwtToken(request);
    String username = null;

    if (jwtToken != null) {
      username = jwtUtils.getUsernameFromToken(jwtToken);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      authenticateUser(request, username, jwtToken);
    }

    filterChain.doFilter(request, response);
  }

  @Nullable
  private String extractJwtToken(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    } else {
      log.warn("JWT Token does not begin with Bearer String or is missing"); // Improved logging
      return null;
    }
  }

  private void authenticateUser(HttpServletRequest request, String username, String jwtToken) {
    UserDetails userDetails = userService.loadUserByUsername(username);

    if (jwtUtils.validateToken(jwtToken, userDetails)) {
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }
}

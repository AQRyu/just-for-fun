package com.aqryuz.backend.authentication.config;

import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.authentication.util.JwtUtils;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

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

    try {
      String jwtToken = jwtUtils.extractJwtToken(request);
      String username = null;

      if (jwtToken != null) {
        username = jwtUtils.getUsernameFromToken(jwtToken);
      }

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        authenticateUser(request, username, jwtToken);
      }

      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      log.error("Unknown JWT Error", e);
      response.setStatus(401);
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

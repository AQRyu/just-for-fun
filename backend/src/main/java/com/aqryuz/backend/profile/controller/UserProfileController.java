package com.aqryuz.backend.profile.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import com.aqryuz.backend.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
  private final UserProfileService profileService;

  @GetMapping("/me")
  public UserProfileRecord getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    return profileService.getProfileByUserId(user.getId());
  }
}

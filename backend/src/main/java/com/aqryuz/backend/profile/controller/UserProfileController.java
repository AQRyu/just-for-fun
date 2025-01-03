package com.aqryuz.backend.profile.controller;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import com.aqryuz.backend.profile.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
  private final UserProfileService profileService;

  @PostMapping("/me")
  public UserProfileRecord createCurrentUserProfile(
      @AuthenticationPrincipal UserDetails userDetails,
      @Valid @RequestBody CreateUserProfileRecord createUserProfileRecord) {
    User user = (User) userDetails;
    return profileService.createProfile(user.getId(), createUserProfileRecord);
  }

  @GetMapping("/me")
  public UserProfileRecord getCurrentUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
    User user = (User) userDetails;
    return profileService.getProfileByUserId(user.getId());
  }
}

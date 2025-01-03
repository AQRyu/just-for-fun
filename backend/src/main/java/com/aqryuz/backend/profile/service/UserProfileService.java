package com.aqryuz.backend.profile.service;

import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfileRecord;

public interface UserProfileService {
  public UserProfileRecord createProfile(Long id, CreateUserProfileRecord userProfileRecord);

  UserProfileRecord getProfileByUserId(Long id);
}

package com.aqryuz.backend.profile.service;

import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UpdateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfileRecord;

public interface UserProfileService {
  public UserProfileRecord createProfile(Long id, CreateUserProfileRecord userProfileRecord);

  public UserProfileRecord updateProfile(Long id, UpdateUserProfileRecord updateUserProfileRecord);

  UserProfileRecord getProfileByUserId(Long id);
}

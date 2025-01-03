package com.aqryuz.backend.profile.service;

import com.aqryuz.backend.profile.model.UserProfileRecord;

public interface UserProfileService {

  UserProfileRecord getProfileByUserId(Long id);
}

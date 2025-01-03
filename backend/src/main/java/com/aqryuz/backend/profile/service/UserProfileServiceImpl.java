package com.aqryuz.backend.profile.service;

import com.aqryuz.backend.profile.exception.UserProfileNotFoundException;
import com.aqryuz.backend.profile.mapper.UserProfileMapper;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import com.aqryuz.backend.profile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileRepository userProfileRepository;
  private final UserProfileMapper userProfileMapper;

  public UserProfileRecord getProfileByUserId(Long id) {
    return userProfileRepository
        .findByUserId(id)
        .map(userProfileMapper::userProfileToUserProfileRecord)
        .orElseThrow(() -> new UserProfileNotFoundException(id));
  }
}

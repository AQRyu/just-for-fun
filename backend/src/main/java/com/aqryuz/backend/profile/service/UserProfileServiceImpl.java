package com.aqryuz.backend.profile.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.profile.exception.UserNotFoundException;
import com.aqryuz.backend.profile.exception.UserProfileNotFoundException;
import com.aqryuz.backend.profile.mapper.UserProfileMapper;
import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UpdateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfile;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import com.aqryuz.backend.profile.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileRepository userProfileRepository;
  private final UserProfileMapper userProfileMapper;
  private final UserService userService;

  @Override
  public UserProfileRecord createProfile(Long id, CreateUserProfileRecord userProfileRecord) {
    UserProfile userProfile = userProfileMapper.userProfileRecordToUserProfile(userProfileRecord);
    User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    userProfile.setUser(user);
    UserProfile savedProfile = userProfileRepository.save(userProfile);
    return userProfileMapper.userProfileToUserProfileRecord(savedProfile);
  }

  @Override
  public UserProfileRecord updateProfile(
      Long userId, UpdateUserProfileRecord updateUserProfileRecord) {
    UserProfile existingProfile =
        userProfileRepository
            .findByUserId(userId)
            .orElseThrow(() -> new UserProfileNotFoundException(userId));

    userProfileMapper.updateUserProfileFromRecord(existingProfile, updateUserProfileRecord);

    UserProfile savedProfile = userProfileRepository.save(existingProfile);
    return userProfileMapper.userProfileToUserProfileRecord(savedProfile);
  }

  public UserProfileRecord getProfileByUserId(Long id) {
    return userProfileRepository
        .findByUserId(id)
        .map(userProfileMapper::userProfileToUserProfileRecord)
        .orElseThrow(() -> new UserProfileNotFoundException(id));
  }
}

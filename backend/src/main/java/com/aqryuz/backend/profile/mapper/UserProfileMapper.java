package com.aqryuz.backend.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UpdateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfile;
import com.aqryuz.backend.profile.model.UserProfileRecord;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

  @Mapping(target = "userId", source = "user.id")
  UserProfileRecord userProfileToUserProfileRecord(UserProfile userProfile);

  @Mapping(target = "user", ignore = true)
  UserProfile userProfileRecordToUserProfile(UserProfileRecord userProfileRecord);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  UserProfile userProfileRecordToUserProfile(CreateUserProfileRecord createUserProfileRecord);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  UserProfile userProfileRecordToUserProfile(UpdateUserProfileRecord updateUserProfileRecord);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  UserProfile updateUserProfileFromRecord(
      @MappingTarget UserProfile existingProfile, UpdateUserProfileRecord updateUserProfileRecord);
}

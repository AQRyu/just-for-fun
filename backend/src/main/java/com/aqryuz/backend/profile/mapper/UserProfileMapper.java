package com.aqryuz.backend.profile.mapper;

import com.aqryuz.backend.profile.model.CreateUserProfileRecord;
import com.aqryuz.backend.profile.model.UserProfile;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

  @Mapping(target = "userId", source = "user.id")
  UserProfileRecord userProfileToUserProfileRecord(UserProfile userProfile);

  @Mapping(target = "user", ignore = true)
  UserProfile userProfileRecordToUserProfile(UserProfileRecord userProfileRecord);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  UserProfile userProfileRecordToUserProfile(CreateUserProfileRecord createUserProfileRecord);
}

package com.aqryuz.backend.profile.mapper;

import com.aqryuz.backend.profile.model.UserProfile;
import com.aqryuz.backend.profile.model.UserProfileRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

  UserProfileRecord userProfileToUserProfileRecord(UserProfile userProfile);

  UserProfile userProfileRecordToUserProfile(UserProfileRecord userProfileRecord);
}

package com.aqryuz.backend.mapper;

import org.mapstruct.Mapper;

import com.aqryuz.backend.authentication.dto.User;
import com.aqryuz.backend.authentication.dto.UserRegistrationResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserRegistrationResponse userToUserRegistrationResponse(User user);

}

package com.aqryuz.backend.mapper;

import org.mapstruct.Mapper;

import com.aqryuz.backend.authentication.controller.payload.UserRegistrationResponse;
import com.aqryuz.backend.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserRegistrationResponse userToUserRegistrationResponse(User user);

}

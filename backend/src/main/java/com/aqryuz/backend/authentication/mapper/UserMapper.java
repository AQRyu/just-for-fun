package com.aqryuz.backend.authentication.mapper;

import org.mapstruct.Mapper;

import com.aqryuz.backend.authentication.controller.payload.UserRegistrationResponse;
import com.aqryuz.backend.authentication.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserRegistrationResponse userToUserRegistrationResponse(User user);

}

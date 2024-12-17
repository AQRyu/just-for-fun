package com.aqryuz.backend.authentication.mapper;

import com.aqryuz.backend.authentication.controller.payload.UserRegistrationResponse;
import com.aqryuz.backend.authentication.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserRegistrationResponse userToUserRegistrationResponse(User user);
}

package com.aqryuz.backend.groupchat.mapper;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.groupchat.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.groupchat.model.Group;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupChatMapper {
  @Mapping(target = "name", source = "request.groupName")
  @Mapping(target = "id", ignore = true)
  Group toGroup(GroupChatCreationRequest request, Set<User> members);
}

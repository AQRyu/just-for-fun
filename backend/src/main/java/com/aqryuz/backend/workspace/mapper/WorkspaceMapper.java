package com.aqryuz.backend.workspace.mapper;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.controller.payload.WorkspaceCreationRequest;
import com.aqryuz.backend.workspace.model.Workspace;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "members", source = "members")
  @Mapping(target = "admin", ignore = true)
  Workspace toWorkspace(WorkspaceCreationRequest request, Set<User> members);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "members", ignore = true)
  @Mapping(target = "admin", source = "admin")
  Workspace toWorkspace(WorkspaceCreationRequest request, User admin);
}

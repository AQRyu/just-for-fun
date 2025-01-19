package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import java.util.List;
import java.util.Set;

public interface WorkspaceMemberManagementService {
  List<User> getMembers(Long workspaceId, User admin);

  List<User> addMembers(Long workspaceId, Set<Long> memberIds, User admin);

  List<User> removeMembers(Long workspaceId, Set<Long> memberIds, User admin);
}

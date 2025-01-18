package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.backendexceptions.ApiException;
import com.aqryuz.backend.groupchat.model.Group;
import java.util.Set;

public interface GroupMemberManagementService {
  Group addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser) throws ApiException;

  Group removeMembersFromGroup(Long groupId, Set<Long> memberIds, User currentUser);
}

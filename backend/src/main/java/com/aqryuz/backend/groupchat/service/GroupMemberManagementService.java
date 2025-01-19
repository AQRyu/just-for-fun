package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import java.util.List;
import java.util.Set;

public interface GroupMemberManagementService {
  List<User> getMembersOfGroup(Long groupId, User user);

  List<User> addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser);

  List<User> removeMembersFromGroup(Long groupId, Set<Long> memberIds, User currentUser);
}

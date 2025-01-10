package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.groupchat.model.Group;
import java.util.Set;

public interface GroupJoiningService {
  public Group addMembersToGroup(Long groupId, Set<Long> memberIds, User currentUser);
}

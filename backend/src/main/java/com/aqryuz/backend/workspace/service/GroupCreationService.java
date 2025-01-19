package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.workspace.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.workspace.model.Group;

public interface GroupCreationService {
  public Group createGroup(GroupChatCreationRequest request, Long userId);
}

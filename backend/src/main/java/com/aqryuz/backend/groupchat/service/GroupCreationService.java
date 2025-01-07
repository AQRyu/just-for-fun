package com.aqryuz.backend.groupchat.service;

import com.aqryuz.backend.groupchat.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.groupchat.model.Group;

public interface GroupCreationService {
  public Group createGroup(GroupChatCreationRequest request, Long userId);
}

package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.model.WorkspaceMessage;
import java.util.List;

public interface WorkspaceMessageService {

  WorkspaceMessage createAndSaveMessage(Long workspaceId, String content, String username);

  List<WorkspaceMessage> getMessages(Long workspaceId, User user);
}

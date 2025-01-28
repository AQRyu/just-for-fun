package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.model.Workspace;
import java.util.List;

public interface WorkspaceService {
  List<Workspace> getAllWorkspaces(User admin);

  Workspace getWorkspaceById(Long workspaceId);
}

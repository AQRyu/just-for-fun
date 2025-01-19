package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.workspace.controller.payload.WorkspaceCreationRequest;
import com.aqryuz.backend.workspace.model.Workspace;

public interface WorkspaceCreationService {
  public Workspace create(WorkspaceCreationRequest request, Long userId);
}

package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.workspace.model.Workspace;
import com.aqryuz.backend.workspace.repository.WorkspaceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {

  private final WorkspaceRepository workspaceRepository;

  @Override
  public List<Workspace> getAllWorkspaces(User admin) {
    return workspaceRepository.findAllByAdmin(admin);
  }
}

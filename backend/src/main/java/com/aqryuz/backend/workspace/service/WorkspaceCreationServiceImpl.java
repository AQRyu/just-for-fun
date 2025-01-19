package com.aqryuz.backend.workspace.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.workspace.controller.payload.WorkspaceCreationRequest;
import com.aqryuz.backend.workspace.exception.InvalidWorkspaceCreationException;
import com.aqryuz.backend.workspace.exception.WorkspaceMasterRequiredException;
import com.aqryuz.backend.workspace.mapper.WorkspaceMapper;
import com.aqryuz.backend.workspace.model.Workspace;
import com.aqryuz.backend.workspace.repository.WorkspaceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceCreationServiceImpl implements WorkspaceCreationService {

  private final WorkspaceRepository workspaceRepository;
  private final UserRepository userRepository;
  private final WorkspaceMapper mapper;

  @Override
  public Workspace create(WorkspaceCreationRequest request, Long userId) {
    workspaceRepository
        .findByName(request.name())
        .ifPresent(
            existingWorkspace -> {
              throw new InvalidWorkspaceCreationException();
            });

    User user = userRepository.findById(userId).orElseThrow(WorkspaceMasterRequiredException::new);

    Workspace newWorkspace = mapper.toWorkspace(request, user);

    return workspaceRepository.save(newWorkspace);
  }
}

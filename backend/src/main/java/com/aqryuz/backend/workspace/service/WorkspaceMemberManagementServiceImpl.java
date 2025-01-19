package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.backendexceptions.ApiException;
import com.aqryuz.backend.workspace.exception.UnauthorizedException;
import com.aqryuz.backend.workspace.exception.UserNotFoundException;
import com.aqryuz.backend.workspace.exception.WorkspaceNotFoundException;
import com.aqryuz.backend.workspace.model.Workspace;
import com.aqryuz.backend.workspace.repository.WorkspaceRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberManagementServiceImpl implements WorkspaceMemberManagementService {

  private final WorkspaceRepository workspaceRepository;
  private final UserRepository userRepository;

  @Override
  public List<User> getMembers(Long workspaceId, User user) {
    Workspace workspace =
        workspaceRepository.findById(workspaceId).orElseThrow(WorkspaceNotFoundException::new);
    if (!workspace.getAdmin().equals(user)) {
      throw new UnauthorizedException();
    }

    return workspace.getMembers().stream().toList();
  }

  @Transactional
  @Override
  public List<User> addMembers(Long workspaceId, Set<Long> memberIds, User currentUser)
      throws ApiException {
    Workspace workspace =
        workspaceRepository.findById(workspaceId).orElseThrow(WorkspaceNotFoundException::new);

    if (!workspace.getAdmin().equals(currentUser)) {
      throw new UnauthorizedException();
    }

    Set<User> membersToAdd = new HashSet<>();
    for (Long memberId : memberIds) {
      User member = userRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
      membersToAdd.add(member);
    }

    workspace.getMembers().addAll(membersToAdd);
    return workspaceRepository.save(workspace).getMembers().stream().toList();
  }

  @Transactional
  @Override
  public List<User> removeMembers(Long workspaceId, Set<Long> memberIds, User currentUser) {
    Workspace workspace =
        workspaceRepository.findById(workspaceId).orElseThrow(WorkspaceNotFoundException::new);

    if (!workspace.getAdmin().equals(currentUser)) {
      throw new UnauthorizedException();
    }

    Set<User> membersToRemove = new HashSet<>();
    for (Long memberId : memberIds) {
      User member = userRepository.findById(memberId).orElseThrow(UserNotFoundException::new);
      membersToRemove.add(member);
    }

    workspace.getMembers().removeAll(membersToRemove);
    return workspaceRepository.save(workspace).getMembers().stream().toList();
  }
}

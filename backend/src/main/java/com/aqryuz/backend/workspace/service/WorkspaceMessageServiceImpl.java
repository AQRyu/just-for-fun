package com.aqryuz.backend.workspace.service;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.service.UserService;
import com.aqryuz.backend.workspace.exception.InvalidMessageContentException;
import com.aqryuz.backend.workspace.exception.WorkspaceMasterRequiredException;
import com.aqryuz.backend.workspace.model.Workspace;
import com.aqryuz.backend.workspace.model.WorkspaceMessage;
import com.aqryuz.backend.workspace.repository.WorkspaceMessageRepository;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceMessageServiceImpl implements WorkspaceMessageService {
  private final UserService userService;
  private final WorkspaceMessageRepository workspaceMessageRepository;
  private final WorkspaceService workspaceService;

  @Override
  public WorkspaceMessage createAndSaveMessage(Long workspaceId, String content, String username) {
    User user = (User) userService.loadUserByUsername(username);
    Workspace workspace = workspaceService.getWorkspaceById(workspaceId);

    if (!workspace.getMembers().contains(user) && !workspace.getAdmin().equals(user)) {
      throw new WorkspaceMasterRequiredException();
    }

    if (content == null || content.isBlank()) {
      throw new InvalidMessageContentException();
    }

    WorkspaceMessage newMessage =
        WorkspaceMessage.builder()
            .workspace(workspace)
            .sender(user)
            .content(content)
            .timestamp(Instant.now())
            .build();

    return workspaceMessageRepository.save(newMessage);
  }

  @Override
  public List<WorkspaceMessage> getMessages(Long workspaceId, User user) {
    Workspace workspace = workspaceService.getWorkspaceById(workspaceId);

    if (!workspace.getMembers().contains(user) && !workspace.getAdmin().equals(user)) {
      throw new WorkspaceMasterRequiredException();
    }

    Pageable pageable = PageRequest.of(0, 50, Sort.by("timestamp"));
    return workspaceMessageRepository.findByWorkspaceId(workspaceId, pageable);
  }
}

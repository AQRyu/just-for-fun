package com.aqryuz.backend.workspace.repository;

import com.aqryuz.backend.workspace.model.WorkspaceMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceMessageRepository extends JpaRepository<WorkspaceMessage, Long> {

  List<WorkspaceMessage> findByWorkspaceId(Long workspaceId, Pageable pageable);
}

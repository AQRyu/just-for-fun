package com.aqryuz.backend.workspace.repository;

import com.aqryuz.backend.workspace.model.Workspace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

  Optional<Workspace> findByName(String name);
}

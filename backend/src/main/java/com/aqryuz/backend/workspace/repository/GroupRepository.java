package com.aqryuz.backend.workspace.repository;

import com.aqryuz.backend.workspace.model.Group;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String groupName);
}

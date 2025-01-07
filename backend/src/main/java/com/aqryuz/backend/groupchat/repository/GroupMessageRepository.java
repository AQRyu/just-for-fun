package com.aqryuz.backend.groupchat.repository;

import com.aqryuz.backend.groupchat.model.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {}

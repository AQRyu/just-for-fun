package com.aqryuz.backend.groupchat.service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aqryuz.backend.authentication.model.User;
import com.aqryuz.backend.authentication.repository.UserRepository;
import com.aqryuz.backend.groupchat.controller.payload.GroupChatCreationRequest;
import com.aqryuz.backend.groupchat.exception.GroupMasterRequiredException;
import com.aqryuz.backend.groupchat.exception.InvalidGroupCreationException;
import com.aqryuz.backend.groupchat.mapper.GroupChatMapper;
import com.aqryuz.backend.groupchat.model.Group;
import com.aqryuz.backend.groupchat.repository.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupCreationServiceImpl implements GroupCreationService {

  private final GroupRepository groupRepository;
  private final UserRepository userRepository;
  private final GroupChatMapper mapper;

  @Override
  public Group createGroup(GroupChatCreationRequest request, Long userId) {
    groupRepository
        .findByName(request.groupName())
        .ifPresent(
            existingGroup -> {
              throw new InvalidGroupCreationException();
            });

    User user = userRepository.findById(userId).orElseThrow(GroupMasterRequiredException::new);

    Group newGroup = mapper.toGroup(request, Set.of(user));

    return groupRepository.save(newGroup);
  }
}

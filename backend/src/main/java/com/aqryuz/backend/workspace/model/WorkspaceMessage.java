package com.aqryuz.backend.workspace.model;

import com.aqryuz.backend.authentication.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workspace_messages")
public class WorkspaceMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "workspace_id")
  private Workspace workspace;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @Column(columnDefinition = "TEXT")
  private String content;

  private Instant timestamp;
}

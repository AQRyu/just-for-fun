package com.aqryuz.backend.workspace.model;

import com.aqryuz.backend.authentication.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workspaces")
public class Workspace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @ManyToMany
  @JoinTable(
      name = "workspace_members",
      joinColumns = @JoinColumn(name = "workspace_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private Set<User> members;

  @ManyToOne
  @JoinColumn(name = "admin_id")
  private User admin;
}

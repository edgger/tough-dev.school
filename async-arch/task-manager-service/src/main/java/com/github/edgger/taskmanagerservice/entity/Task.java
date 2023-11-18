package com.github.edgger.taskmanagerservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks", schema = "task_manager_service")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String jiraId;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    private Account account;

}

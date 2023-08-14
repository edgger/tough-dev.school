package com.github.edgger.taskmanagerservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreatedEvt {
    private String taskId;
    private String accountId;
    private String description;
}

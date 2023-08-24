package com.github.edgger.taskmanagerservice.controller;

import com.github.edgger.taskmanagerservice.dto.rest.NewTaskRq;
import com.github.edgger.taskmanagerservice.dto.rest.OpenTaskInfoRs;
import com.github.edgger.taskmanagerservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public void addNew(@RequestBody NewTaskRq rq) {
        taskService.addNew(rq);
    }

    @PostMapping("/shuffle")
    @PreAuthorize("hasAuthority(T(com.github.edgger.taskmanagerservice.entity.AccountRole).MANAGER.toString())")
    public void shuffle() {
        taskService.shuffle();
    }

    @PostMapping("/{task-id}/complete")
    @PreAuthorize("hasAuthority(T(com.github.edgger.taskmanagerservice.entity.AccountRole).WORKER.toString())")
    public void complete(@PathVariable("task-id") String taskId,
                         Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        String userId = attributes.get("userId").toString();
        taskService.complete(taskId, userId);
    }

    @GetMapping
    public ResponseEntity<List<OpenTaskInfoRs>> getCurrentTasks(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        String userId = attributes.get("userId").toString();
        List<OpenTaskInfoRs> tasks = taskService.getAllByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

}

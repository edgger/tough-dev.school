package com.github.edgger.taskmanagerservice.service;

import com.github.edgger.TaskAssignedMsgV1;
import com.github.edgger.TaskCompletedMsgV1;
import com.github.edgger.TaskCreatedMsgV1;
import com.github.edgger.taskmanagerservice.dto.rest.NewTaskRq;
import com.github.edgger.taskmanagerservice.dto.rest.OpenTaskInfoRs;
import com.github.edgger.taskmanagerservice.entity.Account;
import com.github.edgger.taskmanagerservice.entity.Task;
import com.github.edgger.taskmanagerservice.entity.TaskStatus;
import com.github.edgger.taskmanagerservice.repository.AccountRepository;
import com.github.edgger.taskmanagerservice.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AccountRepository accountRepository;
    private final KafkaProducerService kafkaProducer;
    private final ModelMapper modelMapper;

    @Transactional
    public void addNew(NewTaskRq rq) {
        UUID randomId = accountRepository.getRandomId()
                .orElseThrow(() -> new IllegalStateException("Accounts is empty"));
        Account randomAccount = new Account(randomId, null);
        Task task = new Task(null, rq.getDescription(), TaskStatus.INPROGRESS, randomAccount);
        task = taskRepository.save(task);
        TaskCreatedMsgV1 evt = new TaskCreatedMsgV1(task.getId().toString(), task.getAccount().getId().toString(), task.getDescription());
        kafkaProducer.sendTaskCreatedEvent(evt);
    }

    @Transactional
    public void shuffle() {
        List<UUID> allTaskIds = taskRepository.getAllIds();
        List<UUID> allWorkerIds = accountRepository.getAllWorkerIds();
        if (!allWorkerIds.isEmpty()) {
            for (UUID taskId : allTaskIds) {
                UUID randomWorkerId = allWorkerIds.get(ThreadLocalRandom.current().nextInt(allWorkerIds.size()));
                taskRepository.setAccountId(taskId, randomWorkerId);
                TaskAssignedMsgV1 evt = new TaskAssignedMsgV1(taskId.toString(), randomWorkerId.toString());
                kafkaProducer.sendTaskAssignedEvent(evt);
            }
        }
    }

    @Transactional
    public void complete(String taskIdString, String userIdString) {
        UUID taskId = UUID.fromString(taskIdString);
        UUID userId = UUID.fromString(userIdString);
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task with id=" + taskIdString + " and for account id=" + userIdString + " not found"));
        if (!Objects.equals(task.getAccount().getId(), userId)) {
            throw new IllegalArgumentException("Someone else's task");
        }
        task.setStatus(TaskStatus.COMPLETED);
        TaskCompletedMsgV1 evt = new TaskCompletedMsgV1(taskIdString);
        kafkaProducer.sendTaskCompletedEvent(evt);
    }

    public List<OpenTaskInfoRs> getAllByUserId(String userId) {
        return taskRepository.findAllByAccountId(UUID.fromString(userId))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private OpenTaskInfoRs convertToDto(Task entity) {
        return modelMapper.map(entity, OpenTaskInfoRs.class);
    }
}

package com.github.edgger.taskmanagerservice.repository;

import com.github.edgger.taskmanagerservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("select e.id from #{#entityName} e")
    List<UUID> getAllIds();

    @Modifying
    @Query("update #{#entityName} t set t.account.id = :account_id where t.id = :id")
    int setAccountId(@Param("id") UUID taskId, @Param("account_id") UUID accountId);

}

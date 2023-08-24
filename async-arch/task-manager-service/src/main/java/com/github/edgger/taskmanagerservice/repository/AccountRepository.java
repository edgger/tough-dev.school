package com.github.edgger.taskmanagerservice.repository;

import com.github.edgger.taskmanagerservice.entity.Account;
import com.github.edgger.taskmanagerservice.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query(value = "SELECT e.id from task_manager_service.accounts e ORDER BY random() LIMIT 1", nativeQuery = true)
    Optional<UUID> getRandomId();

    @Query("select e.id from #{#entityName} e where e.role='WORKER'")
    List<UUID> getAllWorkerIds();

    @Modifying
    @Query("UPDATE Account a SET a.role = ?2 WHERE a.id = ?1")
    void updateAccountRoleById(UUID id, AccountRole role);
}

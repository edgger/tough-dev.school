package com.github.edgger.billingservice.repository;

import com.github.edgger.billingservice.entity.Account;
import com.github.edgger.billingservice.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    @Modifying
    @Query("UPDATE Account a SET a.role = ?2 WHERE a.id = ?1")
    void updateAccountRoleById(UUID id, AccountRole role);
}

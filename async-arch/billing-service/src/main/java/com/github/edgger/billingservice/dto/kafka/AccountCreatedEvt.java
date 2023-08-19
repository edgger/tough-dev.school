package com.github.edgger.billingservice.dto.kafka;

import com.github.edgger.billingservice.entity.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedEvt {
    private String accountId;
    private AccountRole role;
}

package com.github.edgger.authservice.dto.kafka;

import com.github.edgger.authservice.entity.AccountRole;
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

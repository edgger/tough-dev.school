package com.github.edgger.authservice.dto;

import com.github.edgger.authservice.entity.AccountRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeRq {
    private String username;
    private AccountRoles accountRoles;
}

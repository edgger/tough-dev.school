package com.github.edgger.authservice.dto.rest;

import com.github.edgger.authservice.entity.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeRq {
    private String username;
    private AccountRole accountRole;
}

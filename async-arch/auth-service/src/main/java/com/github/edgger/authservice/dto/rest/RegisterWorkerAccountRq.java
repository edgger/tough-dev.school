package com.github.edgger.authservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWorkerAccountRq {
    private String username;
    private String password;
}

package com.github.edgger.authservice.controller;

import com.github.edgger.authservice.dto.rest.RegisterWorkerAccountRq;
import com.github.edgger.authservice.entity.AccountRole;
import com.github.edgger.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public void registerAccount(@RequestBody RegisterWorkerAccountRq rq) {
        accountService.addNewWorkerAccount(rq);
    }

    @PostMapping("/{account-id}/roles/change")
    @PreAuthorize("hasAuthority(T(com.github.edgger.authservice.entity.AccountRole).ADMIN.toString())")
    public void changeRole(@PathVariable("account-id") UUID accountId,
                           @RequestBody AccountRole role) {
        accountService.changeRole(accountId, role);
    }

}

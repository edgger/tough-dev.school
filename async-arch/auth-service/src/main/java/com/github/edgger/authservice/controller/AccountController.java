package com.github.edgger.authservice.controller;

import com.github.edgger.authservice.dto.RegisterAccountRq;
import com.github.edgger.authservice.entity.AccountRoles;
import com.github.edgger.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(origins = {"${app.security.cors.origin}"})
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public void registerAccount(@RequestBody RegisterAccountRq rq) {
        accountService.addNewAccount(rq);
    }

    @PostMapping("/{account-id}/roles/change")
    @PreAuthorize("hasAuthority(T(com.github.edgger.authservice.entity.AccountRoles).ADMIN.toString())")
    public void changeRole(@PathVariable("account-id") UUID accountId,
                           @RequestBody AccountRoles role) {
        accountService.changeRole(accountId, role);
    }

}

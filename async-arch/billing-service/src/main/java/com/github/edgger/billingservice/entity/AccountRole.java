package com.github.edgger.billingservice.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AccountRole implements GrantedAuthority {
    ADMIN, MANAGER, WORKER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}

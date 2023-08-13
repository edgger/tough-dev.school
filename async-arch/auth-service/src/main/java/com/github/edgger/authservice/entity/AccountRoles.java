package com.github.edgger.authservice.entity;

import org.springframework.security.core.GrantedAuthority;

public enum AccountRoles implements GrantedAuthority {
    ADMIN, MANAGER, WORKER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}

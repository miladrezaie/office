package com.javasampleapproach.jqueryboostraptable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    OP_ACCESS_ADMIN_PANEL,
    OP_NEW_USER,
    OP_EDIT_USER,
    OP_ACCESS_TAJHIZATS;

    @Override
    public String getAuthority() {
        return this.name();
    }
}

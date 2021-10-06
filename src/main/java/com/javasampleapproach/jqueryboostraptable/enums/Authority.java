package com.javasampleapproach.jqueryboostraptable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    OP_ACCESS_ADMIN_PANEL("دسترسی به ادمین پنل"),
    OP_NEW_USER("اضافه کردن کاربر جدید"),
    OP_EDIT_USER("ویرایش کاربر چدید"),
    OP_ACCESS_TAJHIZATS("دسترسی به تجهیزات"),
    OP_ACCESS_ROLES("دسترسی به نقش ها");


    private final String displayName;

    Authority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}

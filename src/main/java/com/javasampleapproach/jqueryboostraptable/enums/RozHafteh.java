package com.javasampleapproach.jqueryboostraptable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum RozHafteh{
    ROZ_SHANBEH("شنبه"),
    ROZ_YEK_SHNBEH("یکشنبه"),
    ROZ_DO_SHNBEH("دوشنبه"),
    ROZ_SE_SHNBEH("سه شنبه"),
    ROZ_CHAR_SHNBEH("چهارشنبه"),
    ROZ_PANJ_SHNBEH("پنجشنبه"),
    ROZ_JOME("جمعه");



    private final String displayName;

    RozHafteh(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return this.name();
    }

}

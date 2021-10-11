package com.javasampleapproach.jqueryboostraptable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    OP_ACCESS_ADMIN_PANEL("دسترسی به ادمین پنل"),
    OP_NEW_USER("اضافه کردن کاربر جدید"),
    OP_EDIT_USER("ویرایش کاربر چدید"),
    OP_ACCESS_TAJHIZATS("دسترسی به تجهیزات"),
    OP_ACCESS_ROLES("دسترسی به نقش ها"),
    OP_ACCESS_JOBS("دسترسی به شغل ها"),
    OP_ACCESS_BRANDS("دسترسی به برند ها"),
    OP_HERASAT("حراست"),
    OP_TASVIRBARDAR("تصویربردار"),
    OP_HAMAHANGIE("هماهنگی"),
    OP_SEDABARDAR("صدابردار"),
    OP_KARGARDAN("کارگردان"),
    OP_TAHIEKONANDEH("تهیه کننده"),
    OP_MODIR_POSHTIBANIT("مدیر پشتیبانی"),
    OP_ANBARDAR("انباردار"),
    OP_TASISAT("تاسیسات"),
    OP_HAML_NAGHL("حمل و نقل");




    private final String displayName;

    Authority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
//        return "Authority{" +
//                "displayName='" + displayName + '\'' +
//                '}';
        return this.name();
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}

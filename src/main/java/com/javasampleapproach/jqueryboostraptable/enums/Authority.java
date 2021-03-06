package com.javasampleapproach.jqueryboostraptable.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    OP_ACCESS_ADMIN_PANEL("ادمین سایت"),
    OP_NEW_USER("اضافه کردن کاربر جدید"),
    OP_EDIT_USER("ویرایش کاربر چدید"),
    OP_ACCESS_TAJHIZATS("دسترسی به تجهیزات"),
    OP_ACCESS_ROLES("دسترسی به نقش ها"),
    OP_ACCESS_JOBS("دسترسی به شغل ها"),
    OP_ACCESS_BRANDS("دسترسی به برند ها"),
    OP_HERASAT("حراست"),
    OP_TASVIRBARDAR_1("تصویربردار1"),
    OP_TASVIRBARDAR_2("تصویربردار2"),
    OP_TASVIRBARDAR_3("تصویربردار3"),
    OP_TASVIRBARDAR_4("تصویربردار4"),
    OP_HAMAHANGIE("هماهنگی"),
    OP_SEDABARDAR("صدابردار"),
    OP_KARGARDAN("کارگردان"),
    OP_TAHIEKONANDEH("تهیه کننده"),
    OP_MODIR_POSHTIBANIT("مدیر پشتیبانی"),
    OP_ANBARDAR("انباردار"),
    OP_TASISAT("تاسیسات"),
    OP_HAML_NAGHL("حمل و نقل"),
    OP_MODEIR_VAHED_DARKHST_KONANDEH("مدیر واحد درخواست کننده تولید"),
    OP_RANANDEH("راننده"),
    OP_user("کاربر عادی"),
    OP_UP_LINK("ارتباطات زمینی و ماهواره ای"),
    OP_ADD_AVAMEL_TO_OFFICE("افزودن عوامل به آفیش"),
    OP_HAZF_AVAMEL_FROM_OFFICE("حذف عوامل از آفیش"),
    OP_ADD_TAJHIZ_TO_USER_IN_OFFICE("افزودن تجهیز به کاربر"),
    OP_HAZF_TAJHIZ_FROM_USER_IN_OFFICE("گرفتن تجهیز از کاربر"),
    OP_LAGHV_OFFICE("لغو کردن آفیش"),
    OP_ADD_TAJHIZ_TO_OFFICE("افزودن تجهیز به آفیش"),
    OP_AFZODAN_OFFICE("افزودن آفیش"),
    OP_LOCATIONS_OFFICE("قسمت مکان ها"),
    OP_PROGRAMS_OFFICE("قسمت برنامه ها"),
    OP_CATEGORY_OFFICE("قسمت دسته بندی ها"),
    OP_CAR_OFFICE("قسمت وسایل نقلیه ها"),
    OP_SHOW_ALL_PROGRAM("قسمت آفیش زدن - انتخاب تمام برنامه ها"),
    OP_DELETE_AND_UPDATE_PROGRAM("حذف و ویرایش برنامه ها"),
    OP_DELETE_OFFICE("حذف آفیش"),
    OP_SINGLE_OFFICE_HAMLONAGHL_PART("قسمت آفیش تکی - بخش مربوط به حمل ونقل"),
    OP_SINGLE_OFFICE_HERASAT_PART("قسمت آفیش تکی - بخش مربوط به حراست"),
    OP_PRINT_OFFICE("پرینت آفیش"),
    OP_PRINT_PDF_OFFICE("خروجی پی دی اف"),
    OP_FIND_BY_JOBS_OFFICE("پیدا کردن آفیش با استفاده از عنوان شغلی"),
    OP_FIND_BY_CATEGORY_OFFICE("پیدا کردن آفیش با استفاده از دسته بندی"),
    OP_FIND_BY_ID_OFFICE("پیدا کردن آفیش با استفاده از آی دی"),
    OP_LOCATIONS_DELETE_OFFICE("حذف مکان"),
    OP_LOCATIONS_UPDATE_OFFICE("ویرایش مکان"),
    OP_PROGRAM_APPROVE("تایید برنامه"),
    ;




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

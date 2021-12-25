package com.javasampleapproach.jqueryboostraptable.enums;

public enum OfficeForm {
    OFFICE_FORM_ESTEDIO_SIMA("آفیش برنامه های استودیویی سیما"),
    OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE("آفیش ضبط برنامه های تولیدی و خبری"),
    OFFICE_FORM_VAHED_SAIAR("آفیش واحد سیار"),
    OFFICE_FORM_ERTEBATAT("آفیش ارتباطات");

    private final String displayName;

    OfficeForm(String displayName) {
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

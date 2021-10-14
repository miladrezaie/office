package com.javasampleapproach.jqueryboostraptable.enums;

public enum OfficeForm {
    OFFICE_FORM_ESTEDIO_SIMA("آفیش برنامه های استدیوی سیما"),
    OFFICE_FORM_BARNAME_TOLIDIE_KHABARIE("آفیش ضبط برنامه های تولیدی و خبری");

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

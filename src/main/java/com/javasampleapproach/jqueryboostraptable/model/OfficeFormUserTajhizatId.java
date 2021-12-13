package com.javasampleapproach.jqueryboostraptable.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfficeFormUserTajhizatId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "officeForms_id")
    private Long officeFormsId;

    @Column(name = "tajhiz_id")
    private Long tajhizId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfficeFormUserTajhizatId)) return false;
        OfficeFormUserTajhizatId that = (OfficeFormUserTajhizatId) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getOfficeFormsId(), that.getOfficeFormsId()) && Objects.equals(getTajhizId(), that.getTajhizId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getOfficeFormsId(), getTajhizId());
    }
}
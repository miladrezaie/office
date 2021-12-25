package com.javasampleapproach.jqueryboostraptable.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCancelOfficeDescriptionId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "officeForms_id")
    private Long officeFormsId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCancelOfficeDescriptionId)) return false;
        UserCancelOfficeDescriptionId that = (UserCancelOfficeDescriptionId) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getOfficeFormsId(), that.getOfficeFormsId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getOfficeFormsId());
    }
}

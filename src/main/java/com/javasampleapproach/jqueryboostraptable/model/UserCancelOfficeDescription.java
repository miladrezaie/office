package com.javasampleapproach.jqueryboostraptable.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@CreationTimestamp
public class UserCancelOfficeDescription {

    @EmbeddedId
    private UserCancelOfficeDescriptionId id = new UserCancelOfficeDescriptionId();

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @JsonManagedReference
    private User user;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "officeForms_id")
    @MapsId("officeFormsId")
    @JsonManagedReference
    private officeForm officeForms;

    @Lob
    @Column(name = "DESCRIPTION", length = 512)
    @NotNull(message = "لطفا دلیل لغو آفیش را ذکر نمایید")
    private String description;

    public UserCancelOfficeDescription(User user, officeForm officeForms,String description) {
        this.id = new UserCancelOfficeDescriptionId(user.getId(), officeForms.getId());
        this.user = user;
        this.officeForms = officeForms;
        this.description = description;
    }
}

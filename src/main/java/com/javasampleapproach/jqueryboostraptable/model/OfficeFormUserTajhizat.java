package com.javasampleapproach.jqueryboostraptable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "officeFormUserTajhizats")
@NoArgsConstructor
@Getter
@Setter
public class OfficeFormUserTajhizat {

    @EmbeddedId
    private OfficeFormUserTajhizatId id = new OfficeFormUserTajhizatId();

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JsonManagedReference
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "officeForms_id")
    @MapsId("officeFormsId")
    @JsonManagedReference
    private officeForm officeForms;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "tajhiz_id")
    @MapsId("tajhizId")
    @JsonManagedReference
    private Tajhizat tajhizat;

    public OfficeFormUserTajhizat(User user, officeForm officeForms, Tajhizat tajhizat) {
        this.id = new OfficeFormUserTajhizatId(user.getId(), officeForms.getId(), tajhizat.getId());
        this.user = user;
        this.officeForms = officeForms;
        this.tajhizat = tajhizat;
    }

}

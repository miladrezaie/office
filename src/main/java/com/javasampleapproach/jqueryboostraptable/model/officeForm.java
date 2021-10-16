package com.javasampleapproach.jqueryboostraptable.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import lombok.*;
import org.springframework.lang.Nullable;


@Entity
@Table(name = "office_form")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class officeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String nameBarname;

    @Column(columnDefinition = "nvarchar(50)")
    private String tarikhsodur;

    @Column(columnDefinition = "nvarchar(50)")
    private String saat_zabt;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull
    private String tahayekonande;

    @OneToMany(mappedBy="officeForm",orphanRemoval = true)
    private Set<Car> cars;

    @ElementCollection(targetClass = RozHafteh.class)
    private List<RozHafteh> rozhafteh;

    //az no sima or zabtie
    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OfficeForm type;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tajhizat> Tajhizats;

    //taied or laghv office_form
    @Column(columnDefinition = "smallint")
    private Integer status;

    @Column(columnDefinition = "nvarchar(25)")
    private String khodro;

    @Column(columnDefinition = "nvarchar(25)")
    private String ranande;

    @Column(columnDefinition = "nvarchar(10)")
    private String ranandeid;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza2;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza3;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza4;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza5;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza6;

    @Column(columnDefinition = "LONGBLOB")
    private String sedaemza;

    @Column(columnDefinition = "LONGBLOB")
    private String mdarkhastemza;

    @Column(columnDefinition = "LONGBLOB")
    private String poshemza;

    @Column(columnDefinition = "LONGBLOB")
    private String hamlonaghlemza;

    @Column(columnDefinition = "LONGBLOB")
    private String anbaremza;

    @Column(columnDefinition = "LONGBLOB")
    private String vherasatemza;

    @Column(columnDefinition = "LONGBLOB")
    private String khherasatemza;

    @Column(columnDefinition = "LONGBLOB")
    private String tahayeemza;

    @Column(columnDefinition = "nvarchar(25)")
    private String saatvorod;

    @Column(columnDefinition = "nvarchar(25)")
    private String saatkhoroj;

    @Column(columnDefinition = "nvarchar(50)")
    private String date_begin;

    @Column(columnDefinition = "nvarchar(50)")
    private String date_end;

}

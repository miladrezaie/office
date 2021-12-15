package com.javasampleapproach.jqueryboostraptable.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@ToString
public class officeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(50)")
    private String tarikhsodur;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن ساعت شروع الزامی است")
    private String saat_zabt;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن ساعت خاتمه الزامی است")
    private String saat_zabt_end;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن نام تهیه کننده الزامی است")
    private String tahayekonande;

    @OneToMany(mappedBy="officeForm",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Car> car;

    //az no sima or zabtie
    @NotNull(message = "وارد کردن نوع برنامه الزامی است")
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OfficeForm type;

    @ManyToOne
    @JoinColumn(name="location_id")
    @JsonManagedReference
    @NotNull(message = "وارد کردن مکان برنامه الزامی است")
    private Location location;

//    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

//    @OneToMany(mappedBy = "officeForms", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OneToMany(mappedBy = "officeForms", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<OfficeFormUserTajhizat> officeFormUserTajhizats;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tajhizat> tajhizatss;

    @Lob
    @Column(name="DESCRIPTION", length=512)
    private String description;

    //taied or laghv office_form
//    @Column(columnDefinition = "smallint",nullable = false)
    @Column(columnDefinition="tinyint(1) default 0")
    private Boolean status ;

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
    @NotNull(message = "وارد کردن روز شروع الزامی است")
    private String date_begin;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن روز خاتمه الزامی است")
    private String date_end;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @NotNull(message = "وارد کردن نام برنامه الزامی است")
    private Program program ;

}

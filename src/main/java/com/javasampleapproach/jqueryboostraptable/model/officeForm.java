package com.javasampleapproach.jqueryboostraptable.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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

//    @Column(columnDefinition = "nvarchar(50)")
//    @Temporal(TemporalType.DATE)
//    private Date tarikhsodur;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن ساعت شروع الزامی است")
    private String saat_zabt;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن ساعت خاتمه الزامی است")
    private String saat_zabt_end;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن نام تهیه کننده الزامی است")
    private String tahayekonande;

//    @OneToMany(mappedBy="officeForm",fetch = FetchType.EAGER,orphanRemoval = false)
//    @JsonBackReference
//    @ManyToMany( fetch=FetchType.LAZY)
//    private List<Car> car;

    @ManyToOne(fetch = FetchType.EAGER)
//    @Nullable
    @JoinColumn(name = "car_id",nullable = true)
    private Car car;

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
    @ManyToMany( fetch=FetchType.LAZY)
    private List<User> users;

//    @OneToMany(mappedBy = "officeForms", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @OneToMany(mappedBy = "officeForms", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<OfficeFormUserTajhizat> officeFormUserTajhizats;

    @OneToMany(mappedBy = "officeForms", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<UserCancelOfficeDescription> userCancelOfficeDescriptions;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tajhizat> tajhizatss;

    @Lob
    @Column(name="DESCRIPTION", length=512)
    private String description;

    //status 1 complate status 0 incomplate
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
    private String hamahangiemaza;

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

    @Column(columnDefinition = "LONGBLOB")
    private String tasisatemza;

    @Column(columnDefinition = "LONGBLOB")
    private String uplinkemza;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن روز شروع الزامی است")
    @Temporal(TemporalType.DATE)
    private Date date_begin;

    @Column(columnDefinition = "nvarchar(50)")
    @NotNull(message = "وارد کردن روز خاتمه الزامی است")
    @Temporal(TemporalType.DATE)
    private Date date_end;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    @NotNull(message = "وارد کردن نام برنامه الزامی است")
    private Program program ;

//    @CreatedDate
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Date createdAt;
//
//    @LastModifiedDate
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

}

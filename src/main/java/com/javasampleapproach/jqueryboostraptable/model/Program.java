package com.javasampleapproach.jqueryboostraptable.model;

import com.javasampleapproach.jqueryboostraptable.enums.RozHafteh;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "programs")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(30)")
    @NotEmpty(message = "نام برنامه نمی تواند خالی باشد")
    @Size(message = "نام برنامه حداقل 3 کاراکتر و حداکثر 30 کاراکتر می تواند باشد")
    private String name;

    @Column(columnDefinition = "nvarchar(50)")
    @NotEmpty(message = "وارد کردن ساعت شروع الزامی است")
    private String saat_zabt;

    @Column(columnDefinition = "nvarchar(50)")
    @NotEmpty(message = "وارد کردن ساعت خاتمه الزامی است")
    private String saat_zabt_end;

    @Column(columnDefinition = "nvarchar(50)")
    @NotEmpty(message = "وارد کردن روز شروع الزامی است")
    private String date_begin;

    @Column(columnDefinition = "nvarchar(50)")
    @NotEmpty(message = "وارد کردن روز خاتمه الزامی است")
    private String date_end;

//    @OneToOne(cascade = CascadeType.ALL)
//    private officeForm office_form;

    @ElementCollection(targetClass = RozHafteh.class)
    @NotNull(message = "وارد کردن روز هفته الزامی است")
    private List<RozHafteh> rozhafteh;

    @OneToMany(mappedBy = "program" ,cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<officeForm> office_form;

}
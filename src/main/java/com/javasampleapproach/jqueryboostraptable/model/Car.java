package com.javasampleapproach.jqueryboostraptable.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(30)")
    @NotBlank(message = "نام وسیله نقلیه نمی تواند خالی باشد")
    @Size(message = "نام وسیله نقلیه حداقل 3 کاراکتر و حداکثر 30 کاراکتر می تواند باشد",min = 2,max = 30)
    private String name;

    @Column(columnDefinition = "nvarchar(30)")
//    @Pattern(regexp = "/^[0-9]{2}[آابپتثجچحخدذرزژسشصضطظعغفقکگلمنوهی][0-9]{3}-[0-9]{2}$/",message = "فرمت ارسالی پلاک اشتباه است")
    @NotBlank(message = " پلاک خودرو نمی تواند خالی باشد")
    private String plak_number;

    @Column(columnDefinition = "nvarchar(30)")
    @NotBlank(message = "لطفا رنگ خوردو را وارد کنید")
    private String color;

    @Column(columnDefinition = "nvarchar(40)")
    @NotBlank(message = "لطفا نوع خوردو را وارد کنید به عنوان مثال : ون ، اتوبوس ، سواری و ..")
    private String type;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @Nullable
//    @JsonManagedReference
//    @JoinColumn(name = "officeForm_id",nullable = true)
//    private officeForm officeForm;

    @OneToMany(mappedBy = "car",fetch = FetchType.EAGER,orphanRemoval = false)
    @Nullable
    @JsonBackReference
    private Set<officeForm> officeForm;
//    private Set<User> users;
//    @JsonIgnore
//    @ManyToMany(fetch=FetchType.LAZY,mappedBy = "car",cascade = CascadeType.DETACH)
//    private List<officeForm> officeforms;

}

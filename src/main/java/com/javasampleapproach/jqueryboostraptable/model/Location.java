package com.javasampleapproach.jqueryboostraptable.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "locations")
@Setter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Valid
    @Size(message = "نام مکان حداقل 3 کاراکتر و حداکثر 40 کاراکتر می تواند باشد",min = 3,max = 40)
    @NotNull(message = "نام مکان نمی تواند خالی باشد")
    private String name;


    @OneToMany(mappedBy="location")
    private Set<Tajhizat> tajhiz;


    @OneToMany(mappedBy="location")
    private Set<officeForm> officeForms;
}

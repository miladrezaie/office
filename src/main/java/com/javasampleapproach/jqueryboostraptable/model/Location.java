package com.javasampleapproach.jqueryboostraptable.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.Valid;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "locations")
@Setter
public class Location {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition="nvarchar(40)")
    @Valid
    private String name;


    @OneToMany(mappedBy="location",orphanRemoval = true)
    private Set<Tajhizat> tajhiz;


    @OneToMany(mappedBy="location",orphanRemoval = true)
    private Set<officeForm> officeForms;
}

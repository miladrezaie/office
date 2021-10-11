package com.javasampleapproach.jqueryboostraptable.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Brands")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(20)")
    private String name;

    @OneToMany(mappedBy="brand",orphanRemoval = true)
    private Set<Tajhizat> tajhiz;
}

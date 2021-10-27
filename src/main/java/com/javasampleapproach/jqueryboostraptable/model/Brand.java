package com.javasampleapproach.jqueryboostraptable.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Column(columnDefinition = "nvarchar(30)")
    @NotNull(message = "نام برند نمی تواند خالی باشد")
    @Size(message = "نام برند حداقل 3 کاراکتر و حداکثر 30 کاراکتر می تواند باشد")
    private String name;

    @OneToMany(mappedBy="brand")
    @JsonBackReference
    private Set<Tajhizat> tajhiz;
}

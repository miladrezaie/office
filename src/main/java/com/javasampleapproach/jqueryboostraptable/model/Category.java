package com.javasampleapproach.jqueryboostraptable.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(30)")
    @NotBlank(message = "نام دسته بندی نمی تواند خالی باشد")
    @Size(message = "نام دسته بندی حداقل 3 کاراکتر و حداکثر 30 کاراکتر می تواند باشد",min = 2,max = 30)
    private String name;

    @OneToMany(mappedBy = "job")
    private Set<User> users;

}

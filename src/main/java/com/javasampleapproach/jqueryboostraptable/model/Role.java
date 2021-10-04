package com.javasampleapproach.jqueryboostraptable.model;


import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ElementCollection(targetClass = Authority.class)
    private List<Authority> authorities;

//    @ManyToMany(cascade = {CascadeType.DETACH,
//            CascadeType.MERGE,
//            CascadeType.REFRESH,
//            CascadeType.PERSIST})
//    @JoinTable(
//            name = "role_user",
//            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "user_id"})})
//    private List<User> users;
}
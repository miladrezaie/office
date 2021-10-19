package com.javasampleapproach.jqueryboostraptable.model;


import com.javasampleapproach.jqueryboostraptable.enums.Authority;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty(message = "وارد کردن نام نقش کاربری الزامی است")
    private String name;

    @NotEmpty(message = "وارد کردن توضیحات برای نقش کاربری الزامی است")
    private String description;

    @ManyToMany(mappedBy = "roles" ,fetch = FetchType.LAZY)
    private Set<User> users;

    @ElementCollection(targetClass = Authority.class)
    @NotEmpty(message = "وارد کردن سطح دسترسی الزامی است")
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

    @Override
    public String toString(){
        return this.name;
    }
}
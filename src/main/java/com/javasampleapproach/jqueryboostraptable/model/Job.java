package com.javasampleapproach.jqueryboostraptable.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(20)")
    private String name;

    //is ok
//    @JsonIgnore
//    @ManyToMany(mappedBy = "jobs",fetch = FetchType.LAZY)
//    private List<Employee> employees;


    @OneToMany(mappedBy = "job", orphanRemoval = true)
    private Set<User> users;

    @Override
    public String toString() {
        return this.name;
    }
}

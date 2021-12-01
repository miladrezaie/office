package com.javasampleapproach.jqueryboostraptable.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Column(columnDefinition = "nvarchar(40)")
    @Size(message = "نام عنوان شغلی حداقل 1 کاراکتر و حداکثر 40 کاراکتر می تواند باشد",min = 1,max = 40)
    @NotNull(message = "نام عنوان شغلی نمی تواند خالی باشد")
    private String name;

    //is ok
//    @JsonIgnore
//    @ManyToMany(mappedBy = "jobs",fetch = FetchType.LAZY)
//    private List<Employee> employees;

    @OneToMany(mappedBy = "job")
    private Set<User> users;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

    @Override
    public String toString() {
        return this.name;
    }
}

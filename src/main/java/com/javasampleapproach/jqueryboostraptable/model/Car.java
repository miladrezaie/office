package com.javasampleapproach.jqueryboostraptable.model;

import com.javasampleapproach.jqueryboostraptable.enums.OfficeForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
    private String name;

    @Column(columnDefinition = "nvarchar(30)")
    private String plak_number;

    @Column(columnDefinition = "nvarchar(10)")
    private String color;

    @Column(columnDefinition = "nvarchar(10)")
    private String type;

    @ManyToOne
    @Nullable
    @JoinColumn(name="officeForm_id")
    private officeForm officeForm;


}

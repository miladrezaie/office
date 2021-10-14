package com.javasampleapproach.jqueryboostraptable.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "form_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormJob implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(columnDefinition="nvarchar(20)")
    private String job;

    private Integer uid;

    private Long car;

    private Long fid;
    
    private Integer tid;

    @Column(columnDefinition="nvarchar(20)")
    private String khodro;
    
    private Integer taeid;
    

	@Override
	public String toString() {
		return "FormJob [id=" + id + ", job=" + job + ", uid=" + uid + ", fid=" + fid + "]";
	}

}
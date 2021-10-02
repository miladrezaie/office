package com.javasampleapproach.jqueryboostraptable.model;



import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name = "form_job")
public class FormJob implements Serializable{


	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(columnDefinition="nvarchar(20)")
    private String job;

    private Integer uid;
    
    private Long fid;
    
    private Integer tid;

    @Column(columnDefinition="nvarchar(20)")
    private String khodro;
    
    private Integer taeid;
    
    public FormJob() {}
	
	public FormJob(Long id, String job, Integer uid, Long fid, Integer tid, Integer taeid) {
		super();
		this.id = id;
		this.job = job;
		this.uid = uid;
		this.fid = fid;
		this.tid = tid;
		this.taeid = taeid;
	}

	public String getKhodro() {
		return khodro;
	}

	public void setKhodro(String khodro) {
		this.khodro = khodro;
	}

	public Integer getTaeid() {
		return taeid;
	}

	public void setTaeid(Integer taeid) {
		this.taeid = taeid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	@Override
	public String toString() {
		return "FormJob [id=" + id + ", job=" + job + ", uid=" + uid + ", fid=" + fid + "]";
	}

}
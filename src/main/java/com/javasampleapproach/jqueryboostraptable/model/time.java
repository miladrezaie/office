package com.javasampleapproach.jqueryboostraptable.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bar")
@JsonIgnoreProperties(value = {"date", "time"}, allowGetters = true)
public class time implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "barcode")
	private String barcode;
	
	@Column(name = "date")
	private String date;
	
	@Column(name = "time")
	private Integer time;
	
	@Column(name = "brd_barcode")
	private Integer brd_barcode;
	
	public time() {}

	public time(Integer id, String barcode, String date, Integer time, Integer brd_barcode) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.date = date;
		this.time = time;
		this.brd_barcode = brd_barcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getBrd_barcode() {
		return brd_barcode;
	}

	public void setBrd_barcode(Integer brd_barcode) {
		this.brd_barcode = brd_barcode;
	}
	
	
}

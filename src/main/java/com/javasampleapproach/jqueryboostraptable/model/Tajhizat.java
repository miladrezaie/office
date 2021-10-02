package com.javasampleapproach.jqueryboostraptable.model;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "Tajhizat")
public class Tajhizat implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(columnDefinition="nvarchar(20)")
	private String name;
	
	@Column(columnDefinition="nvarchar(30)")
	private String amvalid;

	@Column(columnDefinition="nvarchar(30)")
	private String serial_id;

	@Column(columnDefinition="nvarchar(20)")
	private String type;


	@ManyToOne
	@Nullable
	@JoinColumn(name="location_id")
	private Location location;


	@Column(columnDefinition="nvarchar(20)")
	private String brand;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy = "Tajhizats")
	private List<officeForm> tofficeforme;
	
	@Column(columnDefinition="LONGBLOB")
	private String img;
	
	public Tajhizat() {
	
	}

	public Tajhizat(Integer id, String name, String amvalid,String serial_id, String type, String brand,String img) {
		super();
		this.id = id;
		this.name = name;
		this.amvalid = amvalid;
		this.serial_id = serial_id;
		this.type = type;
		this.brand = brand;

		this.img = img;
	}


	public List<officeForm> getTofficeforme() {
		return tofficeforme;
	}

	public void setTofficeforme(List<officeForm> tofficeforme) {
		this.tofficeforme = tofficeforme;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmvalid() {
		return amvalid;
	}

	public void setAmvalid(String amvalid) {
		this.amvalid = amvalid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerial_id() {
		return serial_id;
	}

	public void setSerial_id(String serial_id) {
		this.serial_id = serial_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Tajhizat [id=" + id + ", name=" + name + ", amvalid=" + amvalid 
				+  "]";
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}


}

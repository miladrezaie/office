package com.javasampleapproach.jqueryboostraptable.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Tajhizat")
public class Tajhizat implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@ManyToMany(mappedBy = "tajhizats", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@OneToMany(mappedBy = "tajhizat", cascade = CascadeType.ALL)
	@JsonBackReference
//	@JsonIgnore
	private Set<OfficeFormUserTajhizat> officeFormUserTajhizats;

	public Set<OfficeFormUserTajhizat> getOfficeFormUserTajhizats() {
		return officeFormUserTajhizats;
	}

	public void setOfficeFormUserTajhizats(Set<OfficeFormUserTajhizat> officeFormUserTajhizats) {
		this.officeFormUserTajhizats = officeFormUserTajhizats;
	}

	@Column(columnDefinition="nvarchar(60)")
	@NotBlank(message = "وارد کردن نام تجهیز الزامی است")
	private String name;
	
	@Column(columnDefinition="nvarchar(60)")
	@NotBlank(message = "وارد کردن شناسه اموال الزامی است")
	private String amvalid;

	@Column(columnDefinition="nvarchar(40)")
	@NotBlank(message = "وارد کردن شناسه سریال تجهیز الزامی است")
	private String serial_id;

	@Column(columnDefinition="nvarchar(40)")
	@NotBlank(message = "وارد کردن نوع الزامی است")
	private String type;

	@ManyToOne
	@JoinColumn(name="location_id")
	@JsonManagedReference
	@NotNull(message = "وارد کردن مکان قرار گیری تجهیز الزامی است")
	private Location location;

	@ManyToOne
	@JoinColumn(name="brand_id")
	@JsonManagedReference
	@NotNull(message = "وارد کردن برند الزامی است")
	private Brand brand;

	@Column(columnDefinition="nvarchar(50)")
	@NotBlank(message = "وارد کردن مدل الزامی است")
	private String model;

	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY,mappedBy = "tajhizatss",cascade = CascadeType.DETACH)
	private List<officeForm> tofficeforme;

	@Column(columnDefinition="LONGBLOB")
	private String img;
	
	public Tajhizat() {

	}

	public Tajhizat(Long id, String name, String amvalid,String serial_id, String type, String img) {
		super();
		this.id = id;
		this.name = name;
		this.amvalid = amvalid;
		this.serial_id = serial_id;
		this.type = type;
		this.img = img;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
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

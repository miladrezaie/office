package com.javasampleapproach.jqueryboostraptable.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "au")
public class employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", columnDefinition = "nvarchar(20)")
    private String first_name;

    @Column(name = "last_name", columnDefinition = "nvarchar(20)")
    private String last_name;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "mac_address")
    private String mac;

    @Column(name = "imei")
    private String imei;

    public employee() {
    }

    public employee(Integer id, String first_name, String last_name, String barcode, String mac_address) {
        super();
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.barcode = barcode;
        this.mac = mac_address;
    }


    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMac_address() {
        return mac;
    }

    public void setMac_address(String mac_address) {
        this.mac = mac_address;
    }

    @Override
    public String toString() {
        return "employee [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", barcode=" + barcode
                + ", mac=" + mac + ", imei=" + imei + "]";
    }


}

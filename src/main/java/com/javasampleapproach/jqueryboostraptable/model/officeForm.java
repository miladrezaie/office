package com.javasampleapproach.jqueryboostraptable.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "office_form")
@NoArgsConstructor
@AllArgsConstructor
public class officeForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "nvarchar(20)")
    private String nameBarname;

    @Column(columnDefinition = "nvarchar(20)")
    private String tahayekonande;

    @Column(columnDefinition = "nvarchar(50)")
    private String tarikh_begin;

    @Column(columnDefinition = "nvarchar(50)")
    private String tarikh_end;

    @Column(columnDefinition = "nvarchar(50)")
    private String tarikhsodur;

    @Column(columnDefinition = "nvarchar(50)")
    private String saat;

    @Column(columnDefinition = "nvarchar(25)")
    private String location;

    public String getTarikh_begin() {
        return tarikh_begin;
    }

    public void setTarikh_begin(String tarikh_begin) {
        this.tarikh_begin = tarikh_begin;
    }

    public String getTarikh_end() {
        return tarikh_end;
    }

    public void setTarikh_end(String tarikh_end) {
        this.tarikh_end = tarikh_end;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> users;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tajhizat> Tajhizats;

    @Column(columnDefinition = "smallint")
    private Integer laghv;

    @Column(columnDefinition = "nvarchar(25)")
    private String khodro;

    @Column(columnDefinition = "nvarchar(25)")
    private String ranande;

    @Column(columnDefinition = "nvarchar(10)")
    private String ranandeid;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza2;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza3;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza4;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza5;

    @Column(columnDefinition = "LONGBLOB")
    private String tasviremza6;

    @Column(columnDefinition = "LONGBLOB")
    private String sedaemza;

    @Column(columnDefinition = "LONGBLOB")
    private String mdarkhastemza;

    @Column(columnDefinition = "LONGBLOB")
    private String poshemza;

    @Column(columnDefinition = "LONGBLOB")
    private String hamlonaghlemza;

    @Column(columnDefinition = "LONGBLOB")
    private String vherasatemza;

    @Column(columnDefinition = "LONGBLOB")
    private String khherasatemza;

    @Column(columnDefinition = "LONGBLOB")
    private String tahayeemza;

    @Column(columnDefinition = "nvarchar(25)")
    private String saatvorod;

    @Column(columnDefinition = "nvarchar(25)")
    private String saatkhoroj;



    public String getTasviremza2() {
        return tasviremza2;
    }

    public void setTasviremza2(String tasviremza2) {
        this.tasviremza2 = tasviremza2;
    }

    public String getTasviremza3() {
        return tasviremza3;
    }

    public void setTasviremza3(String tasviremza3) {
        this.tasviremza3 = tasviremza3;
    }

    public String getTasviremza4() {
        return tasviremza4;
    }

    public void setTasviremza4(String tasviremza4) {
        this.tasviremza4 = tasviremza4;
    }

    public String getTasviremza5() {
        return tasviremza5;
    }

    public void setTasviremza5(String tasviremza5) {
        this.tasviremza5 = tasviremza5;
    }

    public String getTasviremza6() {
        return tasviremza6;
    }

    public void setTasviremza6(String tasviremza6) {
        this.tasviremza6 = tasviremza6;
    }

    public String getTahayeemza() {
        return tahayeemza;
    }

    public void setTahayeemza(String tahayeemza) {
        this.tahayeemza = tahayeemza;
    }

    public String getTasviremza() {
        return tasviremza;
    }

    public void setTasviremza(String tasviremza) {
        this.tasviremza = tasviremza;
    }

    public String getSedaemza() {
        return sedaemza;
    }

    public void setSedaemza(String sedaemza) {
        this.sedaemza = sedaemza;
    }

    public String getSaatvorod() {
        return saatvorod;
    }

    public void setSaatvorod(String saatvorod) {
        this.saatvorod = saatvorod;
    }

    public String getSaatkhoroj() {
        return saatkhoroj;
    }

    public void setSaatkhoroj(String saatkhoroj) {
        this.saatkhoroj = saatkhoroj;
    }

    public Long getId() {
        return id;
    }

    public String getMdarkhastemza() {
        return mdarkhastemza;
    }

    public void setMdarkhastemza(String mdarkhastemza) {
        this.mdarkhastemza = mdarkhastemza;
    }

    public String getPoshemza() {
        return poshemza;
    }

    public void setPoshemza(String poshemza) {
        this.poshemza = poshemza;
    }

    public String getHamlonaghlemza() {
        return hamlonaghlemza;
    }

    public void setHamlonaghlemza(String hamlonaghlemza) {
        this.hamlonaghlemza = hamlonaghlemza;
    }

    public String getVherasatemza() {
        return vherasatemza;
    }

    public void setVherasatemza(String vherasatemza) {
        this.vherasatemza = vherasatemza;
    }

    public String getKhherasatemza() {
        return khherasatemza;
    }

    public void setKhherasatemza(String khherasatemza) {
        this.khherasatemza = khherasatemza;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBarname() {
        return nameBarname;
    }

    public void setNameBarname(String nameBarname) {
        this.nameBarname = nameBarname;
    }

    public String getTarikhsodur() {
        return tarikhsodur;
    }

    public void setTarikhsodur(String tarikhsodur) {
        this.tarikhsodur = tarikhsodur;
    }

    public String getTahayekonande() {
        return tahayekonande;
    }

    public void setTahayekonande(String tahayekonande) {
        this.tahayekonande = tahayekonande;
    }


    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Tajhizat> getTajhizats() {
        return Tajhizats;
    }

    public void setTajhizats(List<Tajhizat> tajhizats) {
        Tajhizats = tajhizats;
    }

    public Integer getLaghv() {
        return laghv;
    }

    public void setLaghv(Integer laghv) {
        this.laghv = laghv;
    }

    public String getKhodro() {
        return khodro;
    }

    public void setKhodro(String khodro) {
        this.khodro = khodro;
    }

    public String getRanande() {
        return ranande;
    }

    public void setRanande(String ranande) {
        this.ranande = ranande;
    }

    public String getRanandeid() {
        return ranandeid;
    }

    public void setRanandeid(String ranandeid) {
        this.ranandeid = ranandeid;
    }


}

package com.javasampleapproach.jqueryboostraptable.model;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.enabled;


@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "*Please provide your personal id")
    @Column(name = "PERSONAL_ID", columnDefinition = "nvarchar(10)")
    private String personalId;

    @Column(name = "NAME", columnDefinition = "nvarchar(20)")
    @NotEmpty(message = "*Please provide your first name")
    private String FName;

    @Column(name = "LAST_NAME", columnDefinition = "nvarchar(20)")
    @NotEmpty(message = "*Please provide your last name")
    private String Lname;

    @Column(name = "PASSWORD")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    @JsonIgnore
    private String pass;

    @Column(columnDefinition = "nvarchar(2)")
    private String finger;

//    @Column(columnDefinition = "nvarchar(20)")
//    private String job;

    @Column(name = "ACTIVE")
    private int active;

    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private String emza;

    @Column(columnDefinition = "nvarchar(25)")
    private String fullname;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<officeForm> officeforms;

    @ManyToOne
    @Nullable
    @JoinColumn(name="job_id")
    private Job job ;

    @JoinTable(
            name = "role_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "user_id"})}
    )
    @ManyToMany
    private Set<Role> roles;

//	@JsonIgnore
//	@ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
//	private Set<Role> roles;

    public User() {
    }

    public User(Integer id, @NotEmpty(message = "*Please provide your personal id") String personalId,
                @NotEmpty(message = "*Please provide your first name") String fName,
                @NotEmpty(message = "*Please provide your last name") String lname,
                @Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String pass,
                String finger,  int active, String emza) {
        super();
        this.id = id;
        this.personalId = personalId;
        this.FName = fName;
        this.Lname = lname;
        this.pass = pass;
        this.finger = finger;
        this.active = active;
        this.emza = emza;
    }

    public List<officeForm> getOfficeforms() {
        return officeforms;
    }

    public void setOfficeforms(List<officeForm> officeforms) {
        this.officeforms = officeforms;
    }

    public Integer getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    public Job getJob() {
        return job;
    }

    public void setJob( Job job) {
        this.job = job;
    }

    public String getEmza() {
        return emza;
    }

    public void setEmza(String emza) {
        this.emza = emza;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonalId() {
        return personalId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }


    public String getFName() {
        return FName;
    }


    public void setFName(String fName) {
        this.FName = fName;
    }


    public String getLname() {
        return Lname;
    }


    public void setLname(String lname) {
        this.Lname = lname;
    }


    public String getPass() {
        return pass;
    }


    public void setPass(String pass) {
        this.pass = pass;
    }


    public String getFinger() {
        return finger;
    }


    public void setFinger(String finger) {
        this.finger = finger;
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "User [id=" + id + ", personalId=" + personalId + ", FName=" + FName + ", Lname=" + Lname + "]";
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return getPass();
    }

    @Override
    public String getUsername() {
        return getPersonalId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

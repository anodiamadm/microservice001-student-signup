package com.anodiam.StudentSignup.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mst_user",
        uniqueConstraints={@UniqueConstraint(name="uk_username", columnNames="username")},
        indexes={@Index(name="idx_username", columnList="username")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private BigInteger userId;

    @Column(nullable = false)
    private int active;

    @Column(nullable = false, name="date_created")
    private Date dateCreated;

    @Column(nullable = false, length = 511)
    private String password;

    @Column(nullable = false, length = 255)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
//    @JsonManagedReference
    private List<Role> roleList = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.active=1;
        this.dateCreated = new Date();
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    protected User(){}

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
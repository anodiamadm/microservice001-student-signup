package com.anodiam.StudentSignup.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "role_mst")
public class Role {
	
	@Id
	@Column(name = "role_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	private String roleName;
	
	@ManyToMany(mappedBy = "roleList")
	@JsonBackReference
	@JsonIgnore
	private Collection<User> userList = new ArrayList<>();

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
	}

	public Long getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@JsonIgnore
	public Collection<User> getUserList() {
		return userList;
	}

	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}
}

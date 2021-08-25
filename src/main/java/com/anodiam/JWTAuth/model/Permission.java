package com.anodiam.JWTAuth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permission_mst")
public class Permission {
	
	@Id
	@Column(name = "permission_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long permissionId;
	private String permissionName;

	@ManyToMany(mappedBy="permissionList")
	@JsonBackReference
	@JsonIgnore
	private Collection<User> userList = new ArrayList<>();

	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}

	public Permission() {
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	@JsonBackReference
	@JsonIgnore
	public Collection<User> getUserList() {
		return userList;
	}

	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}
}

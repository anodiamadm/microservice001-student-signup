package com.anodiam.StudentSignup.model;

//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "mst_role",
		uniqueConstraints={@UniqueConstraint(name="uk_role_name", columnNames="role_name")},
		indexes={@Index(name="idx_role_name", columnList="role_name")})
public class Role {

	@Id
	@Column(name = "role_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private BigInteger roleId;

	@Column(name = "role_name", nullable = false, updatable = false, length = 255)
	private String roleName;

	@ManyToMany(mappedBy = "roleList")
//	@JsonBackReference
//	@JsonIgnore
	private Collection<User> userList = new ArrayList<>();

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

//	@JsonBackReference
//	@JsonIgnore
	public Collection<User> getUserList() {
		return userList;
	}

	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}
}

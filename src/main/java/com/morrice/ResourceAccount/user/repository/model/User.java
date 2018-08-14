package com.morrice.ResourceAccount.user.repository.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.morrice.ResourceAccount.user.repository.IUser;

@Entity
@Table(name = "users")
@DynamicUpdate//Don't Work
public class User implements IUser {

	private static final long serialVersionUID = -1611939462692429597L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	private Integer userIdSignIn;
	
	@Transient
	private UserAuth userAuth;	
	
	@Column(updatable=false)
	@CreationTimestamp
	@JsonProperty(access = Access.WRITE_ONLY)
	private Timestamp createDateTime;

	@Column
	@UpdateTimestamp
	private Timestamp updateDateTime;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Integer getUserIdSignIn() {
		return userIdSignIn;
	}

	@Override
	public void setUserIdSignIn(Integer userIdSignIn) {
		this.userIdSignIn = userIdSignIn;
	}

	@Override
	public UserAuth getUserAuth() {
		return userAuth;
	}

	@Override
	public void setUserAuth(UserAuth userAuth) {
		this.userAuth = userAuth;
	}

	@Override
	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	@Override
	@CreationTimestamp
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	@UpdateTimestamp
	@Version
	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	@Override
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

}

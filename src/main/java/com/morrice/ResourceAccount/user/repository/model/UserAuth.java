package com.morrice.ResourceAccount.user.repository.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.morrice.ResourceAccount.user.repository.IUserAuth;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserAuth implements IUserAuth{

	private static final long serialVersionUID = 323620765598474426L;

	private Integer id;

	private String login;

	private String password;

	private Timestamp createDateTime;

	private Timestamp updateDateTime;
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getLogin() {
		return login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Timestamp getCreateDateTime() {
		return this.createDateTime;
	}

	@Override
	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	@Override
	public Timestamp getUpdateDateTime() {
		return this.updateDateTime;
	}

	@Override
	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}	
	
}

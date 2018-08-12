package com.morrice.ResourceAccount.foundation.model;

public class ClientAuth {

	private String grantType;
	
	private String username;
	
	private String password;
	
	private String clientId;
	
	public ClientAuth(String grantType, String username, String password, String clientId) {
		super();
		this.grantType = grantType;
		this.username = username;
		this.password = password;
		this.clientId = clientId;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	

}

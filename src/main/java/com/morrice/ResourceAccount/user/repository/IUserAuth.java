package com.morrice.ResourceAccount.user.repository;

import com.morrice.ResourceAccount.foundation.model.IModel;

public interface IUserAuth extends IModel{

	String getLogin();

	void setLogin(String login);

	String getPassword();

	void setPassword(String password);

}

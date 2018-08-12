 package com.morrice.ResourceAccount.user.repository;

import com.morrice.ResourceAccount.foundation.model.IModel;
import com.morrice.ResourceAccount.user.repository.model.UserAuth;

public interface IUser extends IModel{

	String getName();

	void setName(String name);

	String getEmail();

	void setEmail(String email);

	Integer getUserIdSignIn();

	void setUserIdSignIn(Integer userIdSignIn);

	UserAuth getUserAuth();

	void setUserAuth(UserAuth userAuth);
	
}

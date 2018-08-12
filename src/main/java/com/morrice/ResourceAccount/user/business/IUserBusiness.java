package com.morrice.ResourceAccount.user.business;

import java.util.Optional;

import com.morrice.ResourceAccount.user.repository.IUser;
import com.morrice.ResourceAccount.user.repository.model.User;

public interface IUserBusiness {

	IUser save(User user);

	Optional<User> findById(Integer id);

	Boolean deleteById(Integer id);

	IUser update(User user, Integer id);

}

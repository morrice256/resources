package com.morrice.ResourceAccount.user.business.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.morrice.ResourceAccount.foundation.comunication.impl.RequestServiceHttp;
import com.morrice.ResourceAccount.foundation.exceptions.ComunicationRestException;
import com.morrice.ResourceAccount.user.business.IUserBusiness;
import com.morrice.ResourceAccount.user.repository.IUser;
import com.morrice.ResourceAccount.user.repository.crud.UserRepository;
import com.morrice.ResourceAccount.user.repository.model.User;
import com.morrice.ResourceAccount.user.repository.model.UserAuth;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBusiness implements IUserBusiness{

	private static Logger logger = LoggerFactory.getLogger(UserBusiness.class);
	
	@Autowired
	private UserRepository userRepository;
	
	//TODO: Change object to interface because 
	@Autowired
	private RequestServiceHttp requestServiceHttp;
		
	@Value("${endpoint.partial.save.user}")
	private String saveUserUrl;
	
	@Override
	public IUser save(User user) {
		try {
			UserAuth userAuth = user.getUserAuth();
			ResponseEntity<UserAuth> response = requestServiceHttp.postMessage(UserAuth.class, saveUserUrl, userAuth);
			user.setUserIdSignIn(response.getBody().getId());
			return userRepository.save(user);
		} catch (ComunicationRestException e) {
			//TODO: Change format to return
			logger.error("Fail to try save UserAuth: ", e);
			return null;
		}
	}

	@Override
	public Optional<User> findById(Integer id) {
		return userRepository.findById(id);
	}
	
	@Override
	public IUser update(User user, Integer id) {
		user.setId(id);
		return userRepository.save(user);
	}
	

	@Override
	public Boolean deleteById(Integer id) {
		try {
			userRepository.deleteById(id);
			return Boolean.TRUE;
		} catch (Exception e) {
			//TODO: Add return fails
			return Boolean.FALSE;
		}
			
	}
	
	
	
	
}

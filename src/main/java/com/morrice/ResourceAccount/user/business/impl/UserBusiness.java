package com.morrice.ResourceAccount.user.business.impl;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.morrice.ResourceAccount.foundation.comunication.impl.RequestServiceHttp;
import com.morrice.ResourceAccount.foundation.exceptions.ComunicationRestException;
import com.morrice.ResourceAccount.foundation.exceptions.ConflictRestException;
import com.morrice.ResourceAccount.foundation.exceptions.NotFoundException;
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
			//TODO: Add internationalization
			logger.error("Fail to try save UserAuth: ", e);
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Fail to try save UserAuth: ", e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error server: ", e);
		}
	}

	@Override
	public Optional<User> findById(Integer id) throws ComunicationRestException, ConflictRestException, NotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			
			StringBuilder url = new StringBuilder(saveUserUrl);
			url.append("/");
			url.append( user.get().getUserIdSignIn() );
			
			ResponseEntity<UserAuth> response = requestServiceHttp.getEntity(UserAuth.class, url.toString());
			user.get().setUserAuth(response.getBody());
			return user;	
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@Override
	public IUser update(User user, Integer id) {
		try {
			user.setId(id);			
			Optional<User> userCheck = userRepository.findById(id);
			//TODO: Change validation because this is not better choice.
			if(!userCheck.isPresent()) {
				throw new NotFoundException();
				//throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			return userRepository.save(user);
		} catch (NotFoundException e) {
			//TODO: Replace to friendly message
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found: ", e);
		} catch (ConstraintViolationException e) {
			//TODO: Replace to friendly message
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ConstraintViolationException: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error server: ", e);
		}
	}
	

	@Override
	public ResponseEntity<?> deleteById(Integer id) {
		try {
			userRepository.deleteById(id);
	        return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
			
	}
	
	
	
	
}

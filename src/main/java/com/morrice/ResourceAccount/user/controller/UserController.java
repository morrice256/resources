package com.morrice.ResourceAccount.user.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.morrice.ResourceAccount.foundation.exceptions.ComunicationRestException;
import com.morrice.ResourceAccount.foundation.exceptions.ConflictRestException;
import com.morrice.ResourceAccount.foundation.exceptions.NotFoundException;
import com.morrice.ResourceAccount.user.business.IUserBusiness;
import com.morrice.ResourceAccount.user.repository.IUser;
import com.morrice.ResourceAccount.user.repository.model.User;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private IUserBusiness userBusiness;	
	
	@PostMapping
	public IUser save(@RequestBody User user) {		
		return userBusiness.save(user);
	}
	
	@GetMapping("/{id}")
	public Optional<User> findById(@PathVariable Integer id) throws ComunicationRestException, ConflictRestException, NotFoundException {		
		return userBusiness.findById(id);		
	}
	
	@PutMapping("/{id}")
	public IUser update(@RequestBody User user, @PathVariable Integer id) {		
		return userBusiness.update(user, id);		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletedById(@PathVariable Integer id) {
		return userBusiness.deleteById(id);					
	}
	
}
